package org.ssor.boss.account.transfer;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class AccountListTransfer
{
  private final List<AccountTransfer> accounts;
  private final Integer page;
  private final Integer pages;
  private final Integer limit;
}
