package org.ssor.boss.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.ssor.boss.account.service.AccountAdminService;
import org.ssor.boss.core.exception.AccountCreationException;
import org.ssor.boss.core.exception.NoAccountsFoundException;
import org.ssor.boss.core.exception.UserNotFoundException;
import org.ssor.boss.core.transfer.AccountCreateRequest;
import org.ssor.boss.core.entity.AccountEntity;
import org.ssor.boss.core.transfer.FilterParams;
import org.ssor.boss.core.transfer.PageResult;
import org.ssor.boss.core.transfer.ServiceResponse;
import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Optional;

/**
 * A controller responsible for providing the basic CRUD operations for admins accessing BoSS data.
 *
 * @author John Christman
 * @author Bermond Jon Batistiana
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/admin/v1/accounts",
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class AccountAdminController {
	private final AccountAdminService accountService;

  /**
   * Creates an account from the provided information in a manual fashion, supporting administration.
   *
   * @param payload The request data for creating an account manually.
   * @return A service response describing the state of creating the account.
   * @throws UserNotFoundException If the user the account is to be associated with, does not exist.
   * @throws AccountCreationException If the account could not be created properly.
   */
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ServiceResponse<String> postAccount(@RequestBody @Valid AccountCreateRequest payload)
			throws UserNotFoundException, AccountCreationException {
		return accountService.createAccount(payload);
	}

  /**
   * Gets the account details for an account with the given ID.
   *
   * @param id The ID of the account to get the details for.
   * @return A service response containing the account details.
   * @throws AccountNotFoundException If the account with ID does not exist.
   */
	@GetMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ServiceResponse<AccountEntity> getAccount(@PathVariable Long id) throws AccountNotFoundException {
		return accountService.getAccount(id);
	}

  /**
   * Gets a page of the accounts in the database given the filtering options.
   *
   * @param keyword A short term describing what to search for.
   * @param filter  The type of accounts to filter by.
   * @param sortBy  What to sort the accounts by.
   * @param page    What page to start at.
   * @param count   The number of elements to get.
   * @param sortDir The direction to sort the accounts by.
   * @return A service response containing a page result for the accounts.
   * @throws NoAccountsFoundException If no accounts were found for the filtering options.
   */
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ServiceResponse<PageResult<AccountEntity>> getAccountList(@PathParam("keyword") Optional<String> keyword,
                                                                   @PathParam("filter") Optional<String> filter,
                                                                   @PathParam("sortBy") Optional<String> sortBy,
                                                                   @PathParam("sortDir") Optional<String> sortDir,
                                                                   @PathParam("page") Optional<Integer> page,
                                                                   @PathParam("count") Optional<Integer> count)
			throws NoAccountsFoundException {
		var options = new FilterParams(keyword.orElse(""), filter.orElse(""), sortBy.orElse("DESC"),
		                               sortDir.orElse("false"), page.orElse(0), count.orElse(5));
		return accountService.getAccounts(options);
	}

  /**
   * Attempts to delete an account with the given ID.
   *
   * @param id The ID of the account to delete.
   * @return A service response containing the status of the deletion.
   * @throws AccountNotFoundException If an account with ID does not exist.
   */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ServiceResponse<String> deleteAccount(@PathVariable Long id) throws AccountNotFoundException {
		return accountService.deleteAccount(id);
	}
}
