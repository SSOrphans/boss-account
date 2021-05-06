package org.ssor.boss.account.transfer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ssor.boss.core.entity.Account;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class AccountDTO
{
  private Integer id;
  private String name;
  private Float balance;
  private String type;

  public void setId(Integer id)
  {
    this.id = id % 10000;
  }

  public AccountDTO(Account account)
  {
    this.id = account.getId();
    this.name = account.getName();
    this.balance = account.getBalance();
    this.type = account.getAccountType().toString();
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AccountDTO that = (AccountDTO) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id);
  }
}
