package org.ssor.boss.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ssor.boss.core.entity.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>
{
  @Query("SELECT a FROM Account a JOIN a.users u WHERE u.id = :userId AND a.closed IS NULL")
  List<Account> findAccountsByUser(Integer userId);

  @Query("SELECT a FROM Account a JOIN a.users u WHERE u.id = :userId AND a.closed IS NULL AND a.id = :accountId")
  Optional<Account> findAccountByIdAndUserId(Integer userId, Integer accountId);
}
