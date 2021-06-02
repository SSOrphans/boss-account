package org.ssor.boss.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ssor.boss.account.service.AccountAdminService;
import org.ssor.boss.core.entity.Account;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping(value = { "api/admin/v1" },
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
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

  @DeleteMapping(value = { "/accounts/{id}" })
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public ResponseEntity<String> deleteAccount(@PathVariable Long id) throws AccountNotFoundException
  {
    return accountService.deleteAccount(id);
  }
}
