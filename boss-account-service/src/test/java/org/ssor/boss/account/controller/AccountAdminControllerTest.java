package org.ssor.boss.account.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.account.service.AccountAdminService;
import org.ssor.boss.account.service.AccountListOptions;
import org.ssor.boss.account.transfer.AccountListTransfer;
import org.ssor.boss.account.transfer.AccountTransfer;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;

import javax.security.auth.login.AccountNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AccountAdminControllerTest
{

  @MockBean
  AccountAdminService accountAdminService;

  @InjectMocks
  AccountAdminController accountAdminController;

  static Account stubbedAccount;
  static AccountListTransfer stubbedAccountListTransfer;

  @BeforeAll
  static void setUp()
  {

    var account = new Account();
    account = new Account();
    account.setId(1L);
    account.setName("TestAccount");
    account.setConfirmed(true);
    account.setAccountType(AccountType.ACCOUNT_SAVING);
    account.setActive(true);
    account.setBalance(5.0f);
    account.setBranchId(1);

    stubbedAccount = account;

    List<Account> accountTransferList = new ArrayList<>();
    accountTransferList.add(account);
    stubbedAccountListTransfer = new AccountListTransfer(accountTransferList, 1, 1,1);

  }

  @Test
  void test_canGetAccount() throws AccountNotFoundException
  {
    Mockito.doReturn(stubbedAccount)
           .when(accountAdminService)
           .getAccount(Mockito.anyLong());
    assertEquals(stubbedAccount, accountAdminController.getAccount(1L));
  }

  @Test
  void test_canDeleteAccount() throws AccountNotFoundException
  {
    var response = new ResponseEntity<>("Account Successfully Deleted", HttpStatus.NO_CONTENT);
    Mockito.doReturn(response).when(accountAdminService).deleteAccount(Mockito.anyLong());
    assertEquals(response, accountAdminController.deleteAccount(1L));
  }

  @Test
  void test_canFetchAccountList() throws NoAccountsFoundException
  {
    int limit = 1;
    Mockito.doReturn(stubbedAccountListTransfer).when(accountAdminService).getAccounts(
        Mockito.any(AccountListOptions.class)
    );
  assertEquals(stubbedAccountListTransfer, accountAdminController.getAccountList(Optional.of(""), Optional.of(1), Optional.of(1), Optional
      .of("")));
  }

}
