package org.ssor.boss.account.dto;

import org.junit.jupiter.api.Test;
import org.ssor.boss.account.transfer.AccountDTO;
import org.ssor.boss.account.transfer.UserAccountsDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DtoEvaluationTest
{
  @Test
  void test_canEvaluateAccountDTO()
  {
    AccountDTO dto1 = new AccountDTO();
    dto1.setId(1);
    AccountDTO dto2 = new AccountDTO();
    dto2.setId(1);
    AccountDTO dto3 = new AccountDTO();
    dto3.setId(2);

    assertEquals(dto1, dto2);
    assertNotEquals(dto2, dto3);

    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void test_canEvaluateUserAccountsDTO()
  {

    AccountDTO accountDto1 = new AccountDTO();
    accountDto1.setId(1);
    accountDto1.setType("testType");
    accountDto1.setName("TestAccount1");
    accountDto1.setBalance(123.45f);

    AccountDTO accountDto2 = new AccountDTO();
    accountDto2.setId(2);
    accountDto2.setType("testType");
    accountDto2.setName("TestAccount2");
    accountDto2.setBalance(12.34f);

    List<AccountDTO> list1 = new ArrayList<>();
    List<AccountDTO> list2 = new ArrayList<>();

    list1.add(accountDto1);
    list2.add(accountDto2);

    UserAccountsDTO dto1 = new UserAccountsDTO();
    dto1.setAccounts(list1);
    UserAccountsDTO dto2 = new UserAccountsDTO();
    dto2.setAccounts(list1);
    UserAccountsDTO dto3 = new UserAccountsDTO();
    dto3.setAccounts(list2);

    assertEquals(dto1, dto2);
    assertNotEquals(dto2, dto3);

    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }


}
