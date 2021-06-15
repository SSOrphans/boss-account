package org.ssor.boss.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ssor.boss.account.exception.AccountCreationException;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.account.exception.UserNotFoundException;
import org.ssor.boss.account.repository.AccountRepository;
import org.ssor.boss.account.transfer.AccountListTransfer;
import org.ssor.boss.account.transfer.AccountToManuallyCreatePayload;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;
import org.ssor.boss.core.entity.User;
import org.ssor.boss.core.repository.UserRepository;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountAdminService
{
  @Autowired
  AccountRepository accountRepository;

  @Autowired
  UserRepository userRepository;

  public Account getAccount(Long id) throws AccountNotFoundException
  {
    return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
  }

  public ResponseEntity<String> deleteAccount(Long id) throws AccountNotFoundException
  {
    var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    account.setClosed(LocalDateTime.now());
    account.setActive(false);
    accountRepository.save(account);
    return new ResponseEntity<>("Account Successfully Deleted", HttpStatus.NO_CONTENT);
  }

  public AccountListTransfer getAccounts(AccountListOptions options) throws NoAccountsFoundException
  {
    Pageable pageable = PageRequest.of(
        options.getOffset(),
        options.getLimit(),
        Sort.by(options.getSortDirection(), options.getSortBy(), AccountListOptions.DEFAULT_SORT_COLUMN));
    Optional<Page<Account>> optionalAccounts = Optional
        .ofNullable(accountRepository.findAccountsWithOptions(options.getKeyword(), options.getFilter(), pageable));
    Page<Account> accountPage = optionalAccounts.orElseThrow(NoAccountsFoundException::new);
    List<Account> accountList = accountPage.stream().collect(Collectors.toList());

    if (accountList.isEmpty())
      throw new NoAccountsFoundException();

    return new AccountListTransfer(accountList, accountPage.getNumber() + 1, accountPage.getTotalPages(),
                                   options.getLimit());
  }

  public ResponseService createAccount(AccountToManuallyCreatePayload payload) throws
      UserNotFoundException,
      AccountCreationException
  {
    Long id = Math.abs(UUID.randomUUID().getLeastSignificantBits() % 10000000000000000L);
    List<User> users = new ArrayList<>();
    for (Integer uid : payload.getUserIds())
    {
      users.add(userRepository.findById(uid).orElseThrow(UserNotFoundException::new));
    }
    var accountEntity = new Account();
    accountEntity.setId(id);
    accountEntity.setAccountType(AccountType.values()[payload.getAccountType()]);
    accountEntity.setUsers(users);
    accountEntity.setBranchId(payload.getBranchId());
    accountEntity.setName(payload.getName());
    accountEntity.setBalance(payload.getBalance());
    accountEntity.setOpened(LocalDateTime.parse(payload.getOpened(), DateTimeFormatter.ofPattern("MM/dd/uuuu kk:mm:ss")));
    accountEntity.setClosed(payload.getClosed() != null ?
                            LocalDateTime.parse(payload.getClosed(), DateTimeFormatter.ofPattern("MM/dd/uuuu")) : null);
    accountEntity.setConfirmed(payload.getConfirmed());
    accountEntity.setActive(payload.getActive());

    try
    {
      accountRepository.save(accountEntity);
    }
    catch (DataIntegrityViolationException e)
    {
      throw new AccountCreationException();
    }

    return new ResponseService(HttpStatus.CREATED.value(), "New account created.");
  }
}


