package org.ssor.boss.account.exception;

import org.springframework.http.HttpStatus;

public class NoAccountsFoundException extends Exception
{
  public static final int ERROR_CODE = HttpStatus.NOT_FOUND.value();
  public static final String MESSAGE = "No accounts found";

  public NoAccountsFoundException()
  {
    super(MESSAGE);
  }
}
