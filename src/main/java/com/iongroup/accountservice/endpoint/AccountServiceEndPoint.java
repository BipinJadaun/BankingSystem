package com.iongroup.accountservice.endpoint;

import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.accountservice.model.Account;
import com.iongroup.accountservice.service.AccountIdGenerator;
import com.iongroup.accountservice.service.AccountService;
import com.iongroup.accountservice.service.IAccountService;

public class AccountServiceEndPoint implements IAccountServiceEndPoint {
	
	private final IAccountService accountService;
	private final AccountIdGenerator idGenerator;

	public AccountServiceEndPoint() {
		this.accountService = new AccountService();
		this.idGenerator = new AccountIdGenerator();
	}
	
	@Override
	public Long createAccount(String name, double openingBalance) throws AccountAlreadyExistException {	
		Long accountId = idGenerator.createID();
		Account newAccount = new Account(accountId, name, openingBalance);
		return accountService.createAccount(newAccount);
	}
	
	@Override
	public void closeAccount(Long accountNumber) throws AccountNotExistException {
		accountService.closeAccount(accountNumber);
	}
	

}
