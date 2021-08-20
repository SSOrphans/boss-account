package org.ssor.boss.account.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.exception.AccountCreationException;
import org.ssor.boss.core.exception.NoAccountsFoundException;
import org.ssor.boss.core.exception.UserNotFoundException;
import org.ssor.boss.core.service.AccountService;
import org.ssor.boss.account.service.ResponseService;
import org.ssor.boss.core.transfer.AccountTransfer;
import org.ssor.boss.core.transfer.AccountToCreateTransfer;
import org.ssor.boss.core.transfer.UserAccountsTransfer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AccountControllerTest
{
  @MockBean
  AccountService accountService;

  @InjectMocks
  AccountController accountController;

  private static ResponseEntity<Account> stubbedCreatedResponseEntity;
  private static Account stubbedCreatedAccount;
  private static UserAccountsTransfer stubbedUserAccountDto;
  private static AccountTransfer stubbedAccountTransfer;

  @BeforeAll
  static void setUp()
  {
    AccountTransfer accountTransfer = new AccountTransfer();
    accountTransfer.setId(1L);
    accountTransfer.setType("testType");
    accountTransfer.setName("TestAccount1");
    accountTransfer.setBalance(123.45f);

    List<AccountTransfer> accountList = new ArrayList<>();

    accountList.add(accountTransfer);

    UserAccountsTransfer userAccountsTransfer = new UserAccountsTransfer();
    userAccountsTransfer.setAccounts(accountList);

    stubbedCreatedAccount = Account.builder().build();
    stubbedCreatedResponseEntity = new ResponseEntity<>(stubbedCreatedAccount, HttpStatus.CREATED);
    stubbedUserAccountDto = userAccountsTransfer;
    stubbedAccountTransfer = accountTransfer;
  }

  @Test
  void test_canPostAccountConfirmation() throws
      UserNotFoundException,
      AccountCreationException
  {
    Mockito.doReturn(stubbedCreatedResponseEntity).when(accountService)
           .createAccount(Mockito.any(AccountToCreateTransfer.class));

    assertEquals(stubbedCreatedResponseEntity,
                 accountController.postAccountCreate(new AccountToCreateTransfer()));
  }

  @Test
  void test_canGetUserAccounts() throws NoAccountsFoundException
  {
    Mockito.doReturn(stubbedUserAccountDto).when(accountService).getAccounts(Mockito.anyInt());

    assertEquals(stubbedUserAccountDto, accountController.getUserAccounts(1));
  }

  @Test
  void test_canGetAccount() throws NoAccountsFoundException
  {
    Mockito.doReturn(stubbedAccountTransfer).when(accountService).getAccount(Mockito.anyInt(), Mockito.anyLong());
    assertEquals(stubbedAccountTransfer, accountController.getAccount(1, 1L));
  }
}
