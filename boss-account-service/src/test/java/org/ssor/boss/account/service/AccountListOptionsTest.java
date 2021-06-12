package org.ssor.boss.account.service;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.ssor.boss.core.entity.AccountType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountListOptionsTest
{
  @Test
  void test_canCreateAccountListOptions()
  {
    var optionA = new AccountListOptions(null, null, null, null, null, null);
    assertEquals(AccountListOptions.DEFAULT_SORT_COLUMN, optionA.getSortBy());
    assertEquals(0, optionA.getOffset());
    assertEquals(10, optionA.getLimit());
    assertEquals(Sort.Direction.DESC, optionA.getSortDirection());
    assertEquals(AccountType.ACCOUNT_INVALID, optionA.getFilter());
    assertEquals("", optionA.getKeyword());

    var optionB = new AccountListOptions("test column", "1","2","ASC", "432", "ACCOUNT_SAVING");
    assertEquals("test column", optionB.getSortBy());
    assertEquals(1, optionB.getOffset());
    assertEquals(2, optionB.getLimit());
    assertEquals(Sort.Direction.ASC, optionB.getSortDirection());
    assertEquals(AccountType.ACCOUNT_SAVING, optionB.getFilter());
    assertEquals("432", optionB.getKeyword());
  }

  @Test
  void test_canEvaluateBadFilter(){
    var option = new AccountListOptions(null, null, null, null, null, "BadFilter");
    assertEquals(AccountType.ACCOUNT_INVALID, option.getFilter());

  }
}
