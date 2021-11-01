package org.ssor.boss.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.ssor.boss.account.controller.AccountAdminController;
import org.ssor.boss.core.exception.AccountCreationException;
import org.ssor.boss.core.exception.NoAccountsFoundException;
import org.ssor.boss.core.exception.UserNotFoundException;
import org.ssor.boss.core.transfer.AccountCreateRequest;
import org.ssor.boss.core.entity.AccountEntity;
import org.ssor.boss.core.entity.AccountInfo;
import org.ssor.boss.core.entity.AccountType;
import org.ssor.boss.core.entity.UserEntity;
import org.ssor.boss.core.repository.AccountEntityRepository;
import org.ssor.boss.core.repository.UserEntityRepository;
import org.ssor.boss.core.transfer.FilterParams;
import org.ssor.boss.core.transfer.PageResult;
import org.ssor.boss.core.transfer.ServiceResponse;
import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A service designed for providing the implementation of its respective {@link AccountAdminController}.
 *
 * @author John Christman
 * @author Bermond Jon Batistiana
 */
@Service
@RequiredArgsConstructor
public class AccountAdminService {
	private final AccountEntityRepository accountRepository;
	private final UserEntityRepository userRepository;

	/**
	 * Gets account details, given the ID, of the account to fetch.
	 *
	 * @param id The ID of the account to fetch.
	 * @return A service response carrying the account details.
	 * @throws AccountNotFoundException If no account with the given ID exists.
	 */
	public ServiceResponse<AccountEntity> getAccount(long id) throws AccountNotFoundException {
		return ServiceResponse.success(accountRepository.findById(id).orElseThrow(AccountNotFoundException::new));
	}

	/**
	 * Attempts to delete an account with the given ID.
	 *
	 * @param id The ID of the account to delete.
	 * @return A service response carrying the status of deleting the account.
	 * @throws AccountNotFoundException If no account with the given ID exists.
	 */
	public ServiceResponse<String> deleteAccount(long id) throws AccountNotFoundException {
		final var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
		final var accountInfo = account.getInfo();
		accountInfo.setClosed(LocalDateTime.now(ZoneOffset.UTC));
		accountInfo.setEnabled(false);
		accountInfo.setLocked(true);
		accountRepository.save(account);
		return ServiceResponse.success("Account Successfully Deleted");
	}

	/**
	 * Pages out a list of accounts given the filtering options.
	 *
	 * @param options The options for filtering the account requested.
	 * @return A service response carrying the page results for the accounts requested.
	 * @throws NoAccountsFoundException If no accounts matching the option criteria exist.
	 */
	public ServiceResponse<PageResult<AccountEntity>> getAccounts(FilterParams options)
			throws NoAccountsFoundException {
		final var sortDir = Sort.Direction.fromOptionalString(options.getSortDir()).orElse(Sort.DEFAULT_DIRECTION);
		final var sortBy = options.getSortBy().split(",");
		final var sort = Sort.by(sortDir, sortBy);
		final var pageable = PageRequest.of(options.getPage(), options.getCount(), sort);

		AccountType type;
		try {
			type = AccountType.valueOf(options.getFilter());
		} catch (IllegalArgumentException exception) {
			// Default to INVALID
			type = AccountType.UNDEFINED;
		}

		final var account = accountRepository.findAccountsWithOptions(options.getKeyword(), type, pageable);
		final var optionalAccounts = Optional.ofNullable(account);
		final var accountPage = optionalAccounts.orElseThrow(NoAccountsFoundException::new);
		final var accountList = accountPage.stream().collect(Collectors.toList());
		if (accountList.isEmpty())
			throw new NoAccountsFoundException();

		final var page = accountPage.getNumber() + 1;
		final var pages = accountPage.getTotalPages();
		final var limit = accountPage.getNumberOfElements();
		return ServiceResponse.success(new PageResult<>(accountList, page, pages, limit));
	}

	/**
	 * Allows admins to create an account manually.
	 *
	 * @param payload The request data for creating a new account.
	 * @return A service response carrying the status of the creating the account.
	 * @throws UserNotFoundException    If the user meant to be associated with the account does not exist.
	 * @throws AccountCreationException If there was a problem creating the account.
	 */
	public ServiceResponse<String> createAccount(AccountCreateRequest payload)
			throws UserNotFoundException, AccountCreationException {
		final var id = Math.abs(UUID.randomUUID().getLeastSignificantBits() % 10000000000000000L);
		final var users = new ArrayList<UserEntity>();
		for (Integer uid : payload.getUserIds())
			users.add(userRepository.findById(uid).orElseThrow(UserNotFoundException::new));

		final var accountEntity = new AccountEntity();
		final var accountInfo = new AccountInfo();
		final var info = payload.getInfo();
		accountInfo.setName(info.getName());
		accountInfo.setBalance(info.getBalance());
		accountInfo.setOpened(info.getOpened());
		if (info.getClosed() != null)
			accountInfo.setClosed(info.getClosed());
		accountInfo.setConfirmed(info.isConfirmed());
		accountInfo.setEnabled(info.isEnabled());
		accountInfo.setLocked(info.isLocked());

		accountEntity.setId(id);
		accountEntity.setType(AccountType.values()[payload.getAccountType()]);
		accountEntity.setUsers(users);
		accountEntity.setBranch(payload.getBranchId());
		accountEntity.setInfo(accountInfo);

		try {
			accountRepository.save(accountEntity);
		}
		catch (DataIntegrityViolationException e) {
			throw new AccountCreationException();
		}

		return ServiceResponse.success("New account created.");
	}
}
