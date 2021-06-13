package org.ssor.boss.account.transfer;

import lombok.Getter;
import lombok.Setter;
import org.ssor.boss.core.entity.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class UserAccountsTransfer
{
  List<AccountTransfer> accounts;

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserAccountsTransfer that = (UserAccountsTransfer) o;
    return accounts.equals(that.accounts);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(accounts);
  }

  public void setAccountsFromEntity(List<Account> entities){
    accounts = new ArrayList<>();
    entities.forEach(
        entity -> {
          var dto = new AccountTransfer(entity);
          accounts.add(dto);
        }
    );


  }
}
