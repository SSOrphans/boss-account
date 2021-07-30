package org.ssor.boss.account.transfer;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;
import org.ssor.boss.core.entity.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AccountAdminTransfer
{
  private final Long id;
  private final AccountType accountType;
  private final String name;
  private final Float balance;
  private final LocalDate opened;
  private LocalDate closed;
  private Boolean confirmed = false;
  private Boolean active = false;
  private final Integer branchId;
  private final List<UserTransfer> users;

  public AccountAdminTransfer(Account account){
    this.id = account.getId();
    this.accountType = account.getAccountType();
    this.name = account.getName();
    this.balance = account.getBalance();
    this.opened = account.getOpened();
    this.closed = account.getClosed();
    this.confirmed = account.getConfirmed();
    this.active = account.getActive();
    this.branchId = account.getBranchId();
    this.users = account.getUsers().stream().map(UserTransfer::new).collect(Collectors.toList());
  }
}
