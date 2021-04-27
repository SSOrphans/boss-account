package org.ssor.boss.account.exception;

import org.springframework.http.HttpStatus;

public class AccountTypeNotFoundException extends Exception
{

  public static final int ERROR_CODE = HttpStatus.NOT_FOUND.value();
  public static final String MESSAGE = "Account type does not exist.";

  public AccountTypeNotFoundException()
  {
    super(MESSAGE);
  }
}
