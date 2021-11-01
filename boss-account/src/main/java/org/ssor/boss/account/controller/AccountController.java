package org.ssor.boss.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.ssor.boss.core.exception.AccountCreationException;
import org.ssor.boss.core.exception.NoAccountsFoundException;
import org.ssor.boss.core.exception.UserNotFoundException;
import org.ssor.boss.core.service.AccountService;
import org.ssor.boss.core.transfer.AccountCreateRequest;
import org.ssor.boss.core.entity.AccountEntity;
import org.ssor.boss.core.transfer.SecureAccountDetails;
import org.ssor.boss.core.transfer.ServiceResponse;
import javax.validation.Valid;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * A controller designed for managing CRUD operations on {@link AccountEntity} objects provided by BoSS.
 *
 * @author John Christman
 * @author Bermond Jon Batistiana
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/accounts", produces = { APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE },
                consumes = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE })
public class AccountController {
	private final AccountService accountService;

	/**
	 * Creates a new account given the parameters to do so.
	 *
	 * @param accountParams The parameters needed to create the account.
	 * @return The service response for creating the account.
	 * @throws UserNotFoundException    If the user to create the account for doesn't exist.
	 * @throws AccountCreationException If the account could not be created correctly.
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceResponse<SecureAccountDetails> createAccount(@Valid @RequestBody AccountCreateRequest accountParams)
			throws UserNotFoundException, AccountCreationException {
		return accountService.createAccount(accountParams);
	}

	/**
	 * Gets all the accounts for a user.
	 *
	 * @param userId The ID of the user to get the accounts for.
	 * @return The service response for fetching all the accounts of a user.
	 * @throws NoAccountsFoundException If no accounts for a user were found.
	 */
	@GetMapping(value = "/users/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public ServiceResponse<List<SecureAccountDetails>> getUserAccounts(@PathVariable Long userId)
			throws NoAccountsFoundException {
		return accountService.getAccounts(userId);
	}

	/**
	 * Gets a single account of a user.
	 *
	 * @param userId    The ID of the user to get the account of.
	 * @param accountId The ID of the account to get.
	 * @return The service response for fetching the account of the user.
	 * @throws NoAccountsFoundException If no account was found for the user.
	 */
	@GetMapping(value = "/{accountId}/users/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public ServiceResponse<SecureAccountDetails> getAccount(@PathVariable Integer userId, @PathVariable Long accountId)
			throws NoAccountsFoundException {
		return accountService.getAccount(userId, accountId);
	}
}
