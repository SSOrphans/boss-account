package org.ssor.boss.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ssor.boss.core.entity.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>
{
  @Query("SELECT a FROM Account a WHERE a.userEntity.id = :userId")
  List<Account> findAccountsByUser(Integer userId);
}
