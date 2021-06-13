package org.ssor.boss.account.service;

import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.ssor.boss.core.entity.AccountType;

import java.util.Optional;

@Getter
public class AccountListOptions
{
  public static final String DEFAULT_SORT_COLUMN = "opened";
  private final String keyword;
  private final AccountType filter;
  private final String sortBy;
  private final Integer offset;
  private final Integer limit;
  private final Sort.Direction sortDirection;

  public AccountListOptions(String... options)
  {
    int length = options.length;

    String sortByUnbox = length > 0 ? Optional.ofNullable(options[0]).orElse(DEFAULT_SORT_COLUMN) : DEFAULT_SORT_COLUMN;
    this.sortBy = sortByUnbox.isEmpty() ? DEFAULT_SORT_COLUMN : sortByUnbox;

    this.offset = length > 1 ? Integer.parseInt(Optional.ofNullable(options[1]).orElse("0")) : 0;

    this.limit = length > 2 ? Integer.parseInt(Optional.ofNullable(options[2]).orElse("10")) : 10;

    String directionToggle = length > 3 ? Optional.ofNullable(options[3]).orElse("DESC") : "DESC";
    this.sortDirection = directionToggle.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;

    this.keyword = length > 4 ? Optional.ofNullable(options[4]).orElse("") : "";

    String strFilter = length > 5
                       ? Optional.ofNullable(options[5]).orElse(AccountType.ACCOUNT_INVALID.toString())
                       : AccountType.ACCOUNT_INVALID.toString();
    this.filter = doesAccountTypeExist(strFilter) ? AccountType.valueOf(strFilter) : AccountType.ACCOUNT_INVALID;
  }

  private boolean doesAccountTypeExist(String uncheckedType)
  {
    try
    {
      AccountType.valueOf(uncheckedType);
      return true;
    }
    catch (IllegalArgumentException e)
    {
      return false;
    }
  }
}
