package org.ssor.boss.account.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>
{
  @Query("SELECT a FROM Account a JOIN a.users u WHERE u.id = :userId AND a.closed IS NULL ORDER BY a.accountType")
  List<Account> findAccountsByUser(Integer userId);

  @Query("SELECT a FROM Account a JOIN a.users u WHERE u.id = :userId AND a.closed IS NULL AND a.id = :accountId")
  Optional<Account> findAccountByIdAndUserId(Integer userId, Long accountId);

  @Query("SELECT a FROM Account a " +
         "WHERE concat(a.id,'') LIKE concat('%', :keyword, '%') " +
         "AND (:filter IS NULL OR :#{#filter.index()} = 0  OR :filter = a.accountType) ")
  Page<Account> findAccountsWithOptions(String keyword, AccountType filter, Pageable pageable);

}
