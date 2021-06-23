package org.ssor.boss.account.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ssor.boss.account.exception.AccountCreationException;
import org.ssor.boss.account.exception.AccountTypeNotFoundException;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.account.exception.UserNotFoundException;
import org.ssor.boss.account.repository.AccountRepository;
import org.ssor.boss.account.transfer.AccountTransfer;
import org.ssor.boss.account.transfer.AccountToCreateTransfer;
import org.ssor.boss.account.transfer.UserAccountsTransfer;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;
import org.ssor.boss.core.entity.User;
import org.ssor.boss.core.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AccountServiceTest
{
  @MockBean
  AccountRepository accountRepository;
  @MockBean
  UserRepository userRepository;

  @InjectMocks
  AccountService accountService;

  private static User stubbedUser;
  private static AccountToCreateTransfer stubbedAccountDto;
  private static List<Account> stubbedAccountEntities;
  private static Account stubbedAccount;

  @BeforeAll
  static void setUp()
  {

    User user = new User();

    user.setId(2);
    user.setEnabled(true);
    user.setCreated(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    user.setEmail("testB@email.com");
    user.setUsername("testB");
    user.setPassword("TestPassword");

    AccountToCreateTransfer accountDto = new AccountToCreateTransfer();
    accountDto.setUserId(2);
    accountDto.setAccountType(2);
    accountDto.setBranchId(3);

    Account ae1 = new Account();
    ae1.setId(1L);
    ae1.setName("Test1");
    ae1.setBalance(12.34f);
    ae1.setAccountType(AccountType.ACCOUNT_CHECKING);
    Account ae2 = new Account();
    ae2.setId(2L);
    ae2.setName("Test2");
    ae2.setBalance(56.78f);
    ae2.setAccountType(AccountType.ACCOUNT_SAVING);

    List<Account> accountList = new ArrayList<>();

    accountList.add(ae1);
    accountList.add(ae2);

    stubbedUser = user;
    stubbedAccountDto = accountDto;
    stubbedAccountEntities = accountList;
    stubbedAccount = ae1;
  }

  @Test
  void test_canGetAccounts() throws NoAccountsFoundException
  {
    Mockito.doReturn(stubbedAccountEntities).when(accountRepository).findAccountsByUser(Mockito.anyInt());
    UserAccountsTransfer expectedUserAccountsTransfer = new UserAccountsTransfer();
    expectedUserAccountsTransfer.setAccountsFromEntity(stubbedAccountEntities);

    UserAccountsTransfer actualUserAccountsTransfer = accountService.getAccounts(1);

    assertEquals(expectedUserAccountsTransfer, actualUserAccountsTransfer);
  }

  @Test
  void test_canCreateAccount() throws UserNotFoundException, AccountCreationException
  {

    Mockito.doReturn(Optional.of(stubbedUser)).when(userRepository).findById(Mockito.anyInt());
    Mockito.doReturn(new Account()).when(accountRepository).save(Mockito.any(Account.class));

    ResponseService rs = new ResponseService(HttpStatus.CREATED.value(), "New account created.");

    assertEquals(rs, accountService.createAccount(stubbedAccountDto));
  }

  @Test
  void test_willThrowUserNotFoundException()
  {
    Mockito.doReturn(Optional.empty()).when(userRepository).findById(Mockito.anyInt());

    UserNotFoundException exception =
        assertThrows(UserNotFoundException.class, () ->
            accountService.createAccount(stubbedAccountDto)
        );

    assertEquals(UserNotFoundException.MESSAGE, exception.getMessage());
    assertEquals(404, UserNotFoundException.ERROR_CODE);
  }

  @Test
  void test_willThrowAccountCreationException()
  {
    Mockito.doReturn(Optional.of(new User())).when(userRepository).findById(Mockito.anyInt());
    Mockito.doThrow(new DataIntegrityViolationException("")).when(accountRepository)
           .save(Mockito.any(Account.class));

    AccountCreationException exception =
        assertThrows(AccountCreationException.class, () ->
            accountService.createAccount(stubbedAccountDto)
        );

    assertEquals(AccountCreationException.MESSAGE, exception.getMessage());
    assertEquals(400, AccountCreationException.ERROR_CODE);
  }

  @Test
  void test_willThrowAccountNotFoundException() throws NoAccountsFoundException
  {
    Mockito.doReturn(new ArrayList<>()).when(accountRepository)
           .findAccountsByUser(Mockito.anyInt());

    assertThrows(NoAccountsFoundException.class, () ->
        accountService.getAccounts(0));

  }

  @Test
  void test_canGetAccount() throws NoAccountsFoundException
  {
    Mockito.doReturn(Optional.of(stubbedAccount))
           .when(accountRepository)
           .findAccountByIdAndUserId(Mockito.anyInt(), Mockito.anyLong());

    var expected = new AccountTransfer(stubbedAccount);
    assertEquals(expected, accountService.getAccount(1,1L));
  }

  @Test
  void test_canThrowAccountNotFoundInCanGetAccount() {
    Mockito.doReturn(Optional.empty())
           .when(accountRepository)
           .findAccountByIdAndUserId(Mockito.anyInt(), Mockito.anyLong());

    NoAccountsFoundException exception =
        assertThrows(NoAccountsFoundException.class, () -> {
          accountService.getAccount(1,1L);
        });

    assertEquals(NoAccountsFoundException.MESSAGE, exception.getMessage());
    assertEquals(404, NoAccountsFoundException.ERROR_CODE);
  }
}
