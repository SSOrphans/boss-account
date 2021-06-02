package org.ssor.boss.account.exception;

import org.springframework.http.HttpStatus;

public class AccountCreationException extends Exception
{
  public static final int ERROR_CODE = HttpStatus.BAD_REQUEST.value();
  public static final String MESSAGE = "Account Creation Failed";

  public AccountCreationException()
  {
    super(MESSAGE);
  }
}
