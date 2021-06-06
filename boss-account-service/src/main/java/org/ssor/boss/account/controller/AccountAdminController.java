package org.ssor.boss.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.account.service.AccountAdminService;
import org.ssor.boss.account.service.AccountListOptions;
import org.ssor.boss.account.transfer.AccountListTransfer;
import org.ssor.boss.core.entity.Account;

import javax.security.auth.login.AccountNotFoundException;
import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
@RequestMapping(value = { "api/admin/v1" },
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@CrossOrigin
public class AccountAdminController
{
  @Autowired
  AccountAdminService accountService;

  @GetMapping(value = { "/accounts/{id}" })
  @ResponseStatus(value = HttpStatus.OK)
  public Account getAccount(@PathVariable Long id) throws AccountNotFoundException
  {
    return accountService.getAccount(id);
  }

  @GetMapping(value = { "/accounts" })
  @ResponseStatus(value = HttpStatus.OK)
  public AccountListTransfer getAccountList(
      @PathParam("sortBy") Optional<String> sortBy,
      @PathParam("offset") Optional<Integer> offset,
      @PathParam("limit") Optional<Integer> limit,
      @PathParam("sortDirection") Optional<String> sortDirection
  ) throws NoAccountsFoundException
  {
    var options = new AccountListOptions(
        sortBy.orElse(AccountListOptions.DEFAULT_SORT_COLUMN),
        offset.orElse(0).toString(),
        limit.orElse(5).toString(),
        sortDirection.orElse("false")
    );
    return accountService.getAccounts(options);
  }

  @DeleteMapping(value = { "/accounts/{id}" })
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public ResponseEntity<String> deleteAccount(@PathVariable Long id) throws AccountNotFoundException
  {
    return accountService.deleteAccount(id);
  }
}
