package org.ssor.boss.account.dto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.ssor.boss.account.transfer.AccountDTO;
import org.ssor.boss.account.transfer.AccountToCreateDTO;
import org.ssor.boss.account.transfer.ErrorMessage;
import org.ssor.boss.account.transfer.UserAccountsDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DtoCreationTest
{

  @Test
  void test_canCreateErrorMessage()
  {
    ErrorMessage em = new ErrorMessage(HttpStatus.OK, "TestMessage");

    assertNotNull(em);
    assertEquals(HttpStatus.OK, em.getStatus());
    assertEquals("TestMessage", em.getMessage());
  }

  @Test
  void test_canCreateAccountToCreateDTO()
  {
    AccountToCreateDTO atcdto = new AccountToCreateDTO();

    assertNotNull(atcdto);
  }

  @Test
  void test_canCreateAccountDTO()
  {
    AccountDTO adto = new AccountDTO();

    assertNotNull(adto);
  }

  @Test
  void test_canCreateUserAccountsDTO()
  {
    UserAccountsDTO udto = new UserAccountsDTO();

    assertNotNull(udto);
  }
}
