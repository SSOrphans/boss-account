package org.ssor.boss.account.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ssor.boss.account.repository.AccountRepository;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;
import org.ssor.boss.core.repository.UserRepository;

import javax.security.auth.login.AccountNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

  @BeforeAll
  static void setUp(){
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
    Mockito.doReturn(Optional.of(stubbedAccount)).when(accountRepository).findById(Mockito.anyLong());
    assertEquals(stubbedAccount, accountAdminService.getAccount(1L));
  }

}
