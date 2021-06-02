package org.ssor.boss.account.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ssor.boss.account.exception.AccountCreationException;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.account.exception.UserNotFoundException;
import org.ssor.boss.account.transfer.ErrorMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class RestErrorHandlerTest
{
  @InjectMocks
  RestErrorHandler restErrorHandler;

  @Test
  void test_canCreateRestErrorHandler()
  {
    RestErrorHandler reh = new RestErrorHandler();
    assertNotNull(reh);
  }

  @Test
  void test_canReturnErrorMessageUserNotFoundException()
  {
    ErrorMessage response = restErrorHandler.processUserNotFound();
    assertEquals(HttpStatus.resolve(UserNotFoundException.ERROR_CODE), response.getStatus());
    assertEquals(UserNotFoundException.MESSAGE, response.getMessage());
  }

  @Test
  void test_canReturnErrorMessageAccountCreationException()
  {
    ErrorMessage response = restErrorHandler.processAccountCreationFailed();
    assertEquals(HttpStatus.resolve(AccountCreationException.ERROR_CODE), response.getStatus());
    assertEquals(AccountCreationException.MESSAGE, response.getMessage());
  }

  @Test
  void test_canReturnErrorNoAccountsFoundException()
  {
    ErrorMessage response = restErrorHandler.processNoAccountsFound();
    assertEquals(HttpStatus.resolve(NoAccountsFoundException.ERROR_CODE), response.getStatus());
    assertEquals(NoAccountsFoundException.MESSAGE, response.getMessage());
  }

  @Test
  void test_canReturnMethodArgumentNotValid(){
    ErrorMessage response = restErrorHandler.processInvalidPayload();
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    assertEquals("Invalid Payload Received", response.getMessage());

  }
}
