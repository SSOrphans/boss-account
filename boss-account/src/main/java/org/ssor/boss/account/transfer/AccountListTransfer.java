package org.ssor.boss.account.transfer;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ssor.boss.core.entity.Account;

import java.util.List;

@RequiredArgsConstructor
@Data
public class AccountListTransfer
{
  private final List<Account> accounts;
  private final Integer page;
  private final Integer pages;
  private final Integer limit;
}
