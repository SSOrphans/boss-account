package org.ssor.boss.account.transfer;

import lombok.Data;
import org.ssor.boss.core.entity.Account;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AccountListAdminTransfer
{
  private List<AccountAdminTransfer> accounts;
  private final Integer page;
  private final Integer pages;
  private final Integer limit;

  public AccountListAdminTransfer (List<Account> accounts, Integer page, Integer pages, Integer limit){
    this.page = page;
    this.pages = pages;
    this.limit = limit;
    this.accounts = accounts.stream().map(AccountAdminTransfer::new).collect(Collectors.toList());
  }
}
