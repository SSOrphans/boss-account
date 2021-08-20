package org.ssor.boss.account.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.ssor.boss.core.exception.AccountCreationException;
import org.ssor.boss.core.exception.AccountTypeNotFoundException;
import org.ssor.boss.core.exception.NoAccountsFoundException;
import org.ssor.boss.core.exception.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExceptionObjectTest
{
  @Test
  void test_canCreateAccountCreationException()
  {
    AccountCreationException accountCreationException = new AccountCreationException();
    assertNotNull(accountCreationException);
    assertEquals(AccountCreationException.ERROR_CODE, HttpStatus.BAD_REQUEST.value());
    assertNotNull(accountCreationException.getMessage());
  }

  @Test
  void test_canCreateUserNotFoundException()
  {
    UserNotFoundException userNotFoundException = new UserNotFoundException();
    assertNotNull(userNotFoundException);
    assertEquals(UserNotFoundException.ERROR_CODE, HttpStatus.NOT_FOUND.value());
    assertNotNull(userNotFoundException.getMessage());

  }

  @Test
  void test_canCreateNoAccountsFoundException()
  {
    NoAccountsFoundException noAccountsFoundException = new NoAccountsFoundException();
    assertNotNull(noAccountsFoundException);
    assertEquals(NoAccountsFoundException.ERROR_CODE, HttpStatus.NOT_FOUND.value());
    assertNotNull(noAccountsFoundException.getMessage());
  }

  @Test
  void test_canCreateAccountTypeNotFoundException()
  {
    AccountTypeNotFoundException accountTypeNotFoundException = new AccountTypeNotFoundException();
    assertNotNull(accountTypeNotFoundException);
    assertEquals(AccountTypeNotFoundException.ERROR_CODE, HttpStatus.NOT_FOUND.value());
    assertNotNull(accountTypeNotFoundException.getMessage());

  }

}
