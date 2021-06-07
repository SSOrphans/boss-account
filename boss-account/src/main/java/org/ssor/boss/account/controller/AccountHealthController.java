package org.ssor.boss.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = { "api/v1/accounts/health" })
public class AccountHealthController
{
  @GetMapping(value = "")
  public ResponseEntity<String> accountHealth()
  {
    return new ResponseEntity<>("Healthy", HttpStatus.OK);
  }
}
