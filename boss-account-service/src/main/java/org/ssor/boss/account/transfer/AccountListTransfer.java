package org.ssor.boss.account.transfer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class AccountListTransfer
{
  private final List<AccountTransfer> accounts;
  private final Integer page;
  private final Integer pages;
  private final Integer limit;
}
