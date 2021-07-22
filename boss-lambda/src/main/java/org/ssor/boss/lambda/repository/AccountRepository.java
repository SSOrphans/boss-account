package org.ssor.boss.lambda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ssor.boss.core.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>
{

}
