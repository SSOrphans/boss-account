package org.ssor.boss.account.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

  @Pattern(regexp = "^(0?[1-9]|1[012])[/](0?[1-9]|[12][0-9]|3[01])[/]\\d{4}$")
  private String opened = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/uuuu"));

  @Pattern(regexp = "^(0?[1-9]|1[012])[/](0?[1-9]|[12][0-9]|3[01])[/]\\d{4}$")
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
