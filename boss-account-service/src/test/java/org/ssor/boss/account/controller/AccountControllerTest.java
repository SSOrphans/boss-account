package org.ssor.boss.account.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ssor.boss.account.exception.AccountCreationException;
import org.ssor.boss.account.exception.AccountTypeNotFoundException;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.account.exception.UserNotFoundException;
import org.ssor.boss.account.service.AccountService;
import org.ssor.boss.account.service.ResponseService;
import org.ssor.boss.account.transfer.AccountDTO;
import org.ssor.boss.account.transfer.AccountToCreateDTO;
import org.ssor.boss.account.transfer.UserAccountsDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AccountControllerTest
{
  @MockBean
  AccountService accountService;

  @InjectMocks
  AccountController accountController;

  private static ResponseService stubbedCreatedResponseService;
  private static UserAccountsDTO stubbedUserAccountDto;

  @BeforeAll
  static void setUp()
  {
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setId(1);
    accountDTO.setType("testType");
    accountDTO.setName("TestAccount1");
    accountDTO.setBalance(123.45f);

    List<AccountDTO> accountList = new ArrayList<>();

    accountList.add(accountDTO);

    UserAccountsDTO userAccountsDTO = new UserAccountsDTO();
    userAccountsDTO.setAccounts(accountList);

    stubbedCreatedResponseService = new ResponseService(HttpStatus.CREATED.value(), "New account created.");
    stubbedUserAccountDto = userAccountsDTO;
  }

  @Test
  void test_canPostAccountConfirmation() throws
      UserNotFoundException,
      AccountCreationException,
      AccountTypeNotFoundException
  {
    Mockito.doReturn(stubbedCreatedResponseService).when(accountService)
           .createAccount(Mockito.any(AccountToCreateDTO.class));

    assertEquals(stubbedCreatedResponseService,
                 accountController.postAccountCreate(new AccountToCreateDTO()));
  }

  @Test
  void test_canGetUserAccounts() throws NoAccountsFoundException
  {
    Mockito.doReturn(stubbedUserAccountDto).when(accountService).getAccounts(Mockito.anyInt());

    assertEquals(stubbedUserAccountDto, accountController.getUserAccounts(1));
  }
}
