package org.ssor.boss.account.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ssor.boss.core.entity.AccountType;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
public class AccountToCreateDTO
{
  @JsonProperty("account_type")
  @NotNull
  private Integer accountType;
  @JsonProperty("user_id")
  @NotNull
  private Integer userId;
  @JsonProperty("branch_id")
  private Integer branchId = 0;
}
