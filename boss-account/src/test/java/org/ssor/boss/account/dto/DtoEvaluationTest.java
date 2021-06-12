package org.ssor.boss.account.dto;

import org.junit.jupiter.api.Test;
import org.ssor.boss.account.transfer.AccountTransfer;
import org.ssor.boss.account.transfer.UserAccountsTransfer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DtoEvaluationTest
{
  @Test
  void test_canEvaluateAccountDTO()
  {
    AccountTransfer dto1 = new AccountTransfer();
    dto1.setId(1L);
    AccountTransfer dto2 = new AccountTransfer();
    dto2.setId(1L);
    AccountTransfer dto3 = new AccountTransfer();
    dto3.setId(2L);

    assertEquals(dto1, dto2);
    assertNotEquals(dto2, dto3);

    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void test_canEvaluateUserAccountsDTO()
  {

    AccountTransfer accountTransfer1 = new AccountTransfer();
    accountTransfer1.setId(1L);
    accountTransfer1.setType("testType");
    accountTransfer1.setName("TestAccount1");
    accountTransfer1.setBalance(123.45f);

    AccountTransfer accountTransfer2 = new AccountTransfer();
    accountTransfer2.setId(2L);
    accountTransfer2.setType("testType");
    accountTransfer2.setName("TestAccount2");
    accountTransfer2.setBalance(12.34f);

    List<AccountTransfer> list1 = new ArrayList<>();
    List<AccountTransfer> list2 = new ArrayList<>();

    list1.add(accountTransfer1);
    list2.add(accountTransfer2);

    UserAccountsTransfer dto1 = new UserAccountsTransfer();
    dto1.setAccounts(list1);
    UserAccountsTransfer dto2 = new UserAccountsTransfer();
    dto2.setAccounts(list1);
    UserAccountsTransfer dto3 = new UserAccountsTransfer();
    dto3.setAccounts(list2);

    assertEquals(dto1, dto2);
    assertNotEquals(dto2, dto3);

    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }


}
