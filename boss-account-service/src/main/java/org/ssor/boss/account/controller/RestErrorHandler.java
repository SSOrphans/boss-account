package org.ssor.boss.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.ssor.boss.account.exception.AccountCreationException;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.account.exception.UserNotFoundException;
import org.ssor.boss.account.transfer.ErrorMessage;

@RestControllerAdvice
public class RestErrorHandler
{
  @ExceptionHandler(UserNotFoundException.class)
  @PostMapping(produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ErrorMessage processUserNotFound()
  {
    return new ErrorMessage(HttpStatus.resolve(UserNotFoundException.ERROR_CODE), UserNotFoundException.MESSAGE);
  }

  @ExceptionHandler(AccountCreationException.class)
  @PostMapping(produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public ErrorMessage processAccountCreationFailed()
  {
    return new ErrorMessage(HttpStatus.resolve(AccountCreationException.ERROR_CODE), AccountCreationException.MESSAGE);
  }

  @ExceptionHandler(NoAccountsFoundException.class)
  @GetMapping(produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ErrorMessage processNoAccountsFound()
  {
    return new ErrorMessage(HttpStatus.resolve(NoAccountsFoundException.ERROR_CODE), NoAccountsFoundException.MESSAGE);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @PostMapping(produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public ErrorMessage processInvalidPayload(){
    return new ErrorMessage(HttpStatus.BAD_REQUEST, "Invalid Payload Received");
  }

}
