package org.ssor.boss.account.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.ssor.boss.account.exception.AccountCreationException;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.account.exception.UserNotFoundException;
import org.ssor.boss.account.service.AccountService;
import org.ssor.boss.account.service.ResponseService;
import org.ssor.boss.account.transfer.AccountDTO;
import org.ssor.boss.account.transfer.AccountToCreateDTO;
import org.ssor.boss.account.transfer.UserAccountsDTO;

@RestController
@RequestMapping(value = { "api/accounts" },
                produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
public class AccountController
{
  @Autowired
  AccountService accountService;

  @PostMapping(value = { "" },
               produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseService postAccountCreate(@RequestBody @Valid AccountToCreateDTO accountParams) throws
      UserNotFoundException,
      AccountCreationException
  {
    return accountService.createAccount(accountParams);
  }

  @GetMapping(value = { "/users/{userId}" },
              produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  @ResponseStatus(value = HttpStatus.OK)
  public UserAccountsDTO getUserAccounts(@PathVariable Integer userId) throws
      NoAccountsFoundException
  {
    return accountService.getAccounts(userId);
  }

  @GetMapping(value = "/{accountId}/users/{userId}",
              produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  @ResponseStatus(value = HttpStatus.OK)
  public AccountDTO getAccount(@PathVariable Integer userId, @PathVariable Integer accountId) throws
      NoAccountsFoundException
  {
    return accountService.getAccount(userId, accountId);
  }

}
