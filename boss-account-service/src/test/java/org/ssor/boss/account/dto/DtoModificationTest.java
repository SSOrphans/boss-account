package org.ssor.boss.account.dto;

import org.junit.jupiter.api.Test;
import org.ssor.boss.account.transfer.AccountDTO;
import org.ssor.boss.account.transfer.AccountToCreateDTO;
import org.ssor.boss.account.transfer.UserAccountsDTO;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoModificationTest
{
  @Test
  void test_CanModifyAccountToCreateDto(){
    AccountToCreateDTO atcdto = new AccountToCreateDTO();

    atcdto.setAccountType(1);
    assertEquals(1, atcdto.getAccountType());
    atcdto.setBranchId(2);
    assertEquals(2, atcdto.getBranchId());
    atcdto.setUserId(3);
    assertEquals(3, atcdto.getUserId());
  }

  @Test
  void test_canModifyAccountDto(){
    AccountDTO adto = new AccountDTO();

    adto.setType("TestType");
    assertEquals("TestType", adto.getType());
    adto.setId(1L);
    assertEquals(1, adto.getId());
    adto.setBalance(12.34f);
    assertEquals(12.34f, adto.getBalance());
    adto.setName("TestName");
    assertEquals("TestName", adto.getName());
  }

  @Test
  void test_canModifyUserAccountsDto(){
    UserAccountsDTO uadto = new UserAccountsDTO();
    List<AccountDTO> accounts = new ArrayList<>();
    AccountDTO a = new AccountDTO();
    a.setId(1L);
    AccountDTO b = new AccountDTO();
    b.setId(2L);
    AccountDTO c = new AccountDTO();
    c.setId(3L);
    AccountDTO d = new AccountDTO();
    d.setId(4L);

    accounts.add(a);
    accounts.add(b);
    accounts.add(c);
    accounts.add(d);

    uadto.setAccounts(accounts);
    assertEquals(accounts, uadto.getAccounts());
  }

  @Test
  void test_canModifyUserAccountsDtoFromEntities(){
    UserAccountsDTO uadto = new UserAccountsDTO();

    List<AccountDTO> expectedAccounts = new ArrayList<>();
    AccountDTO expectedA = new AccountDTO();
    expectedA.setId(1L);
    AccountDTO expectedB = new AccountDTO();
    expectedB.setId(2L);

    expectedAccounts.add(expectedA);
    expectedAccounts.add(expectedB);

    List<Account> accounts = new ArrayList<>();
    Account a = new Account();
    Account b = new Account();

    a.setId(1L);
    a.setName("TestA");
    a.setBalance(12.34f);
    a.setAccountType(AccountType.ACCOUNT_CHECKING);

    b.setId(2L);
    b.setName("TestB");
    b.setBalance(67.89f);
    b.setAccountType(AccountType.ACCOUNT_SAVING);

    accounts.add(a);
    accounts.add(b);

    uadto.setAccountsFromEntity(accounts);
    assertEquals(expectedAccounts, uadto.getAccounts());
  }
}
