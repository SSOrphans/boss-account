package org.ssor.boss.account.dto;

import org.junit.jupiter.api.Test;
import org.ssor.boss.core.transfer.AccountTransfer;
import org.ssor.boss.core.transfer.AccountToCreateTransfer;
import org.ssor.boss.core.transfer.UserAccountsTransfer;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoModificationTest
{
  @Test
  void test_CanModifyAccountToCreateDto(){
    AccountToCreateTransfer atcdto = new AccountToCreateTransfer();

    atcdto.setAccountType(1);
    assertEquals(1, atcdto.getAccountType());
    atcdto.setBranchId(2);
    assertEquals(2, atcdto.getBranchId());
    atcdto.setUserId(3);
    assertEquals(3, atcdto.getUserId());
  }

  @Test
  void test_canModifyAccountDto(){
    AccountTransfer adto = new AccountTransfer();

    adto.setType("TestType");
    assertEquals("TestType", adto.getType());
    adto.setId(1L);
    assertEquals(1, adto.getId());
    adto.setBalance(12.34f);
    assertEquals(12.34f, adto.getBalance());
    adto.setName("TestName");
    assertEquals("TestName", adto.getName());
    adto.setConfirmed(true);
    assertEquals(true, adto.getConfirmed());
  }

  @Test
  void test_canModifyUserAccountsDto(){
    UserAccountsTransfer uadto = new UserAccountsTransfer();
    List<AccountTransfer> accounts = new ArrayList<>();
    AccountTransfer a = new AccountTransfer();
    a.setId(1L);
    AccountTransfer b = new AccountTransfer();
    b.setId(2L);
    AccountTransfer c = new AccountTransfer();
    c.setId(3L);
    AccountTransfer d = new AccountTransfer();
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
    UserAccountsTransfer uadto = new UserAccountsTransfer();

    List<AccountTransfer> expectedAccounts = new ArrayList<>();
    AccountTransfer expectedA = new AccountTransfer();
    expectedA.setId(1L);
    AccountTransfer expectedB = new AccountTransfer();
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
