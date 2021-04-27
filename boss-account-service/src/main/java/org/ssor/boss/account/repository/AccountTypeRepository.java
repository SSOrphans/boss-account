package org.ssor.boss.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ssor.boss.core.entity.AccountType;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer>
{
}
