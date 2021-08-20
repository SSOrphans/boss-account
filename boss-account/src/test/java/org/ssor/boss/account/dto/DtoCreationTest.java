package org.ssor.boss.account.dto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.ssor.boss.core.transfer.AccountTransfer;
import org.ssor.boss.core.transfer.AccountToCreateTransfer;
import org.ssor.boss.account.transfer.ErrorMessage;
import org.ssor.boss.core.transfer.UserAccountsTransfer;

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
    AccountToCreateTransfer atcdto = new AccountToCreateTransfer();

    assertNotNull(atcdto);
  }

  @Test
  void test_canCreateAccountDTO()
  {
    AccountTransfer adto = new AccountTransfer();

    assertNotNull(adto);
  }

  @Test
  void test_canCreateUserAccountsDTO()
  {
    UserAccountsTransfer udto = new UserAccountsTransfer();

    assertNotNull(udto);
  }
}
