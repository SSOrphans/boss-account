package org.ssor.boss.account.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;
import org.ssor.boss.core.entity.User;
import org.ssor.boss.core.entity.UserType;
import org.ssor.boss.core.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class AccountRepositoryTest
{
  @Autowired
  public AccountRepository accountRepository;
  @Autowired
  public UserRepository userRepository;

  public static Account stubbedAccount;
  public static User stubbedUser;

  @BeforeAll
  static void setup()
  {
    stubbedUser = new User();
    stubbedAccount = new Account();

    stubbedUser.setEnabled(true);
    stubbedUser.setType(UserType.USER_DEFAULT);
    stubbedUser.setBranchId(1);
    stubbedUser.setCreated(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    stubbedUser.setEmail("testB@email.com");
    stubbedUser.setUsername("testB");
    stubbedUser.setId(2);
    stubbedUser.setPassword("TestPass");

    stubbedAccount.setAccountType(AccountType.ACCOUNT_CHECKING);
    stubbedAccount.setUsers(List.of(stubbedUser));
    stubbedAccount.setActive(true);
    stubbedAccount.setOpened(LocalDate.now());
    stubbedAccount.setName("TestAccount");
    stubbedAccount.setBalance(123.45f);
    stubbedAccount.setConfirmed(false);
    stubbedAccount.setBranchId(1);

  }

  @Test
  void test_canFindId()
  {

    List<Account> foundEntity = accountRepository.findAccountsByUser(1);

    assertNotNull(foundEntity);
  }
}
