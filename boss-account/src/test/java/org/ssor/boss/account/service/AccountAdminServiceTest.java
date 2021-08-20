package org.ssor.boss.account.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.core.repository.AccountRepository;
import org.ssor.boss.account.transfer.AccountListTransfer;
import org.ssor.boss.account.transfer.AccountTransfer;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;
import org.ssor.boss.core.repository.UserRepository;

import javax.security.auth.login.AccountNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AccountAdminServiceTest
{
  @MockBean
  AccountRepository accountRepository;
  @MockBean
  UserRepository userRepository;

  @InjectMocks
  AccountAdminService accountAdminService;

  static Account stubbedAccount;
  static Page<Account> stubbedAccountTransferPage;

  @BeforeAll
  static void setUp()
  {
    var accountA = new Account();
    accountA = new Account();
    accountA.setId(1L);
    accountA.setName("TestAccount");
    accountA.setConfirmed(true);
    accountA.setAccountType(AccountType.ACCOUNT_SAVING);
    accountA.setActive(true);
    accountA.setBalance(5.0f);
    accountA.setBranchId(1);

    var accountB = new Account();
    accountB = new Account();
    accountB.setId(1L);
    accountB.setName("TestAccount");
    accountB.setConfirmed(true);
    accountB.setAccountType(AccountType.ACCOUNT_SAVING);
    accountB.setActive(true);
    accountB.setBalance(5.0f);
    accountB.setBranchId(1);

    List<Account> accountTransfers = new ArrayList<>();
    accountTransfers.add(accountA);
    accountTransfers.add(accountB);

    stubbedAccount = accountA;
    stubbedAccountTransferPage = new PageImpl<>(accountTransfers);
  }

  @Test
  void test_canGetAccount() throws AccountNotFoundException
  {
    Mockito.doReturn(Optional.of(stubbedAccount)).when(accountRepository).findById(Mockito.anyLong());
    assertEquals(stubbedAccount, accountAdminService.getAccount(1L));
  }

  @Test
  void test_canDeleteAccount() throws AccountNotFoundException
  {
    Mockito.doReturn(Optional.of(stubbedAccount)).when(accountRepository).findById(Mockito.anyLong());
    Mockito.doNothing().when(accountRepository).delete(Mockito.any(Account.class));
    var response = new ResponseEntity<>("Account Successfully Deleted", HttpStatus.NO_CONTENT);
    assertEquals(response, accountAdminService.deleteAccount(1L));
  }

  @Test
  void test_canFetchListOfAccounts() throws NoAccountsFoundException
  {
    int limit = 1;
    Mockito.doReturn(stubbedAccountTransferPage).when(accountRepository)
           .findAccountsWithOptions(Mockito.anyString(), Mockito.any(AccountType.class), Mockito.any(Pageable.class));

    List<Account> accountList = stubbedAccountTransferPage.stream().collect(Collectors.toList());
    AccountListTransfer expected = new AccountListTransfer(accountList, stubbedAccountTransferPage.getNumber() + 1,
                                                           stubbedAccountTransferPage.getTotalPages(), limit);
    assertEquals(expected, accountAdminService.getAccounts(
        new AccountListOptions("", "0", Integer.toString(limit), "")
    ));
  }

  @Test
  void test_canThrowAccountNotFoundException()
  {
    Mockito.doReturn(new PageImpl<Account>(new ArrayList<>())).when(accountRepository)
           .findAccountsWithOptions(Mockito.anyString(), Mockito.any(AccountType.class), Mockito.any(Pageable.class));

    NoAccountsFoundException exception =
        assertThrows(NoAccountsFoundException.class, () ->
            accountAdminService.getAccounts(new AccountListOptions("", "1", "1", ""))
        );

    assertEquals(NoAccountsFoundException.MESSAGE, exception.getMessage());
  }

}
