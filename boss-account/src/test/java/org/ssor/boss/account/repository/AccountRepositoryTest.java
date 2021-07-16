package org.ssor.boss.account.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.ssor.boss.core.entity.AccountType;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest
{
  @Autowired
  public AccountRepository accountRepository;

  @Test
  void test_canFindByUserId()
  {
    var foundEntity = accountRepository.findAccountsByUser(1);
    assertFalse(foundEntity.isEmpty());
  }

  @Test
  void test_canFindByAccountIdAndUserId()
  {
    var foundEntity = accountRepository.findAccountByIdAndUserId(1, 420391249212312L);
    assertNotNull(foundEntity);
  }

  @Test
  void test_findAccountsWithOptions()
  {
    Pageable page = PageRequest.of(0, 10);
    var foundEntity = accountRepository.findAccountsWithOptions("1", AccountType.ACCOUNT_SAVING, page);
    assertFalse(foundEntity.isEmpty());
  }

  @Test
  void test_findAccountsWithBadOptions()
  {
    Pageable page = PageRequest.of(0, 10);
    var foundEntity = accountRepository.findAccountsWithOptions("abc", AccountType.ACCOUNT_SAVING, page);
    assertTrue(foundEntity.isEmpty());
  }
}
