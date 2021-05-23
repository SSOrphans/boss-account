package org.ssor.boss.account.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ssor.boss.account.service.AccountAdminService;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;

import javax.security.auth.login.AccountNotFoundException;

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

  }

  @Test
  void test_canGetAccount() throws AccountNotFoundException
  {
    Mockito.doReturn(stubbedAccount)
           .when(accountAdminService)
           .getAccount(Mockito.anyLong());
    assertEquals(stubbedAccount, accountAdminController.getAccount(1L));
  }

}
