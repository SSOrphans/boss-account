package org.ssor.boss.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ssor.boss.account.exception.NoAccountsFoundException;
import org.ssor.boss.account.repository.AccountRepository;
import org.ssor.boss.account.transfer.AccountTransfer;
import org.ssor.boss.account.transfer.AccountListTransfer;
import org.ssor.boss.core.entity.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountAdminService
{
  @Autowired
  AccountRepository accountRepository;

  public Account getAccount(Long id) throws AccountNotFoundException
  {
    return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
  }

  public ResponseEntity<String> deleteAccount(Long id) throws AccountNotFoundException
  {
    var account = accountRepository.findById(id);
    accountRepository.delete(account.orElseThrow(AccountNotFoundException::new));
    return new ResponseEntity<>("Account Successfully Deleted", HttpStatus.NO_CONTENT);
  }

  public AccountListTransfer getAccounts(AccountListOptions options) throws NoAccountsFoundException
  {
    Pageable pageable = PageRequest.of(
        options.getOffset(),
        options.getLimit(),
        Sort.by(options.getSortDirection(), options.getSortBy(), AccountListOptions.DEFAULT_SORT_COLUMN));
    Optional<Page<Account>> optionalAccounts = Optional.ofNullable(accountRepository.findAccountsWithOptions(pageable));
    Page<Account> accountPage = optionalAccounts.orElseThrow(NoAccountsFoundException::new);
    List<AccountTransfer> accountList = accountPage.stream().map(AccountTransfer::new).collect(Collectors.toList());

    if (accountList.isEmpty())
      throw new NoAccountsFoundException();

    return new AccountListTransfer(accountList, accountPage.getNumber() + 1, accountPage.getTotalPages(),
                                   options.getLimit());
  }
}
