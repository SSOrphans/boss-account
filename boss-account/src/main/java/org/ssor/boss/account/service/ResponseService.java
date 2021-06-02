package org.ssor.boss.account.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class ResponseService
{

  private Integer status;
  private String message;

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ResponseService that = (ResponseService) o;
    return status.equals(that.status) && message.equals(that.message);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(status, message);
  }
}
