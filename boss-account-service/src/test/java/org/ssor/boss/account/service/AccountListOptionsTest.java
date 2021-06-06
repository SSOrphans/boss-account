package org.ssor.boss.account.service;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountListOptionsTest
{
  @Test
  void test_canCreateAccountListOptions()
  {
    var optionA = new AccountListOptions(null, null, null, null);
    assertEquals(AccountListOptions.DEFAULT_SORT_COLUMN, optionA.getSortBy());
    assertEquals(0, optionA.getOffset());
    assertEquals(10, optionA.getLimit());
    assertEquals(Sort.Direction.DESC, optionA.getSortDirection());

    var optionB = new AccountListOptions("test column", "1","2","ASC");
    assertEquals("test column", optionB.getSortBy());
    assertEquals(1, optionB.getOffset());
    assertEquals(2, optionB.getLimit());
    assertEquals(Sort.Direction.ASC, optionB.getSortDirection());
  }
}
