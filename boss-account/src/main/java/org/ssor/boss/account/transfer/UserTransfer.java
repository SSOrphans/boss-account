package org.ssor.boss.account.transfer;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ssor.boss.core.entity.User;
import org.ssor.boss.core.entity.UserType;

import javax.persistence.Column;
import javax.persistence.Enumerated;

@Data
public class UserTransfer
{
  private final Integer id;
  private final UserType type;
  private final Integer branchId;
  private final String username;
  private final String email;

  public UserTransfer (User user){
    this.id = user.getId();
    this.type = user.getType();
    this.branchId = user.getBranchId();
    this.username = user.getUsername();
    this.email = user.getEmail();
  }
}
