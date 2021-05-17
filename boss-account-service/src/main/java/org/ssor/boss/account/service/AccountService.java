package org.ssor.boss.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.ssor.boss.account.exception.AccountCreationException;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.account.exception.UserNotFoundException;
import org.ssor.boss.account.repository.AccountRepository;
import org.ssor.boss.account.transfer.AccountDTO;
import org.ssor.boss.account.transfer.AccountToCreateDTO;
import org.ssor.boss.account.transfer.UserAccountsDTO;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;
import org.ssor.boss.core.entity.User;
import org.ssor.boss.core.repository.UserRepository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService
{
  @Autowired
  AccountRepository accountRepository;
  @Autowired
  UserRepository userRepository;

  public UserAccountsDTO getAccounts(Integer userId) throws NoAccountsFoundException
  {
    List<Account> accountEntities = accountRepository.findAccountsByUser(userId);
    if (accountEntities.isEmpty())
      throw new NoAccountsFoundException();

    UserAccountsDTO userAccountsDTO = new UserAccountsDTO();
    userAccountsDTO.setAccountsFromEntity(accountEntities);

    return userAccountsDTO;
  }

  public AccountDTO getAccount(Integer userId, Long accountId) throws NoAccountsFoundException
  {
    Optional<Account> account = accountRepository.findAccountByIdAndUserId(userId, accountId);
    if (account.isEmpty())
      throw new NoAccountsFoundException();

    return new AccountDTO(account.get());

  }

  public ResponseService createAccount(AccountToCreateDTO accountParams) throws
      UserNotFoundException, AccountCreationException
  {

    Long id =Math.abs(UUID.randomUUID().getLeastSignificantBits() % 10000000000000000L);
    User user = userRepository.findById(accountParams.getUserId()).orElseThrow(UserNotFoundException::new);
    AccountType accountType = AccountType.values()[accountParams.getAccountType()];

    Account accountEntity = new Account();
    accountEntity.setId(id);
    accountEntity.setAccountType(accountType);
    accountEntity.setUsers(List.of(user));
    accountEntity.setBranchId(accountParams.getBranchId());
    accountEntity.setName(accountType.name().substring(8));
    accountEntity.setBalance(0f);
    accountEntity.setOpened(LocalDateTime.now());
    accountEntity.setConfirmed(false);
    accountEntity.setActive(false);

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
