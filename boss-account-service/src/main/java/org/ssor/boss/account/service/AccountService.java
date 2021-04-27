package org.ssor.boss.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.ssor.boss.account.exception.AccountCreationException;
import org.ssor.boss.account.exception.AccountTypeNotFoundException;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.account.exception.UserNotFoundException;
import org.ssor.boss.account.repository.AccountRepository;
import org.ssor.boss.account.repository.AccountTypeRepository;
import org.ssor.boss.account.transfer.AccountToCreateDTO;
import org.ssor.boss.account.transfer.UserAccountsDTO;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;
import org.ssor.boss.core.entity.User;
import org.ssor.boss.core.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService
{
  @Autowired
  AccountRepository accountRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  AccountTypeRepository accountTypeRepository;

  public UserAccountsDTO getAccounts(Integer userId) throws NoAccountsFoundException
  {
    List<Account> accountEntities = accountRepository.findAccountsByUser(userId);
    if (accountEntities.isEmpty())
      throw new NoAccountsFoundException();

    UserAccountsDTO userAccountsDTO = new UserAccountsDTO();
    userAccountsDTO.setAccountsFromEntity(accountEntities);

    return userAccountsDTO;
  }

  public ResponseService createAccount(AccountToCreateDTO accountParams) throws
      UserNotFoundException, AccountCreationException, AccountTypeNotFoundException
  {

    User user = userRepository.findById(accountParams.getUserId()).orElseThrow(UserNotFoundException::new);
    AccountType accountType = accountTypeRepository.findById(accountParams.getAccountType()).orElseThrow(
        AccountTypeNotFoundException::new);

    accountType.setName(AccountType.AccountTypes.CARD.getType());
    Account accountEntity = new Account();
    accountEntity.setAccountType(accountType);
    accountEntity.setUserEntity(user);
    accountEntity.setBranchId(accountParams.getBranchId());
    accountEntity.setName(accountParams.getName());
    accountEntity.setBalance(accountParams.getBalance());
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
