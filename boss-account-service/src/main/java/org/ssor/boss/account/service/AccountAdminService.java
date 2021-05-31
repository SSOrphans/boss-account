package org.ssor.boss.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssor.boss.account.repository.AccountRepository;
import org.ssor.boss.core.entity.Account;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class AccountAdminService
{
  @Autowired
  AccountRepository accountRepository;

  public Account getAccount(Long id) throws AccountNotFoundException
  {
    return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
  }
}
