package org.ssor.boss.account.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ssor.boss.account.service.ResponseService;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.Transaction;
import org.ssor.boss.core.exception.AccountCreationException;
import org.ssor.boss.core.exception.NoAccountsFoundException;
import org.ssor.boss.core.exception.UserNotFoundException;
import org.ssor.boss.core.service.AccountService;
import org.ssor.boss.core.transfer.AccountToCreateTransfer;
import org.ssor.boss.core.transfer.AccountTransfer;
import org.ssor.boss.core.transfer.TransactionInput;
import org.ssor.boss.core.transfer.TransactionTransfer;
import org.ssor.boss.core.transfer.UserAccountsTransfer;

@RestController
@RequestMapping(value = { "api/v1/accounts" },
                produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin
public class AccountController
{
  @Autowired
  AccountService accountService;

  @PostMapping(value = { "" },
               produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<Account> postAccountCreate(@RequestBody @Valid AccountToCreateTransfer accountParams) throws
          UserNotFoundException,
          AccountCreationException
  {
    return accountService.createAccount(accountParams);
  }

  @GetMapping(value = { "/users/{userId}" },
              produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  @ResponseStatus(value = HttpStatus.OK)
  public UserAccountsTransfer getUserAccounts(@PathVariable Integer userId) throws
      NoAccountsFoundException
  {
    return accountService.getAccounts(userId);
  }

  @GetMapping(value = "/{accountId}/users/{userId}",
              produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  @ResponseStatus(value = HttpStatus.OK)
  public AccountTransfer getAccount(@PathVariable Integer userId, @PathVariable Long accountId) throws
      NoAccountsFoundException
  {
    return accountService.getAccount(userId, accountId);
  }

  @PostMapping(value = "/payment",
          produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<TransactionTransfer> accountPayment(@RequestBody @Valid TransactionInput transactionInput)
  {
    TransactionTransfer transactionTransfer = accountService.createAccountPayment(transactionInput);
    if(transactionTransfer == null)
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    return new ResponseEntity<>(transactionTransfer,HttpStatus.CREATED);
  }

}
