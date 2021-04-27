package org.ssor.boss.account.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
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
