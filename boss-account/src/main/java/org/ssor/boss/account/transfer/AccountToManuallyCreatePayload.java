package org.ssor.boss.account.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@RequiredArgsConstructor
public class AccountToManuallyCreatePayload
{
  @NotNull
  private final String name;

  @NotNull
  private final Float balance;
  private String opened = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/uuuu kk:mm:ss"));
  private String closed;

  @NotNull
  private final Boolean confirmed;

  @NotNull
  private final Boolean active;

  @NotNull
  @JsonProperty("branch_id")
  private final Integer branchId;

  @NotNull
  @JsonProperty("account_type")
  private final Integer accountType;

  @NotNull
  @JsonProperty("user_ids")
  private final Integer[] userIds;
}
