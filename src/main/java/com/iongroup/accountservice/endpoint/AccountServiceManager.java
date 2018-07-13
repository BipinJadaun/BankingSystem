package com.iongroup.accountservice.endpoint;

import java.time.LocalDate;

import com.iongroup.accountservice.dao.IAccountDao;
import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.accountservice.model.Account;
import com.iongroup.accountservice.service.AccountIdGenerator;
import com.iongroup.accountservice.service.AccountService;
import com.iongroup.accountservice.service.IAccountService;
import com.iongroup.transactionservice.dao.ITransactionDao;

public class AccountServiceManager implements AccountServiceInterface {
	
	private final IAccountService accountService;
	private final AccountIdGenerator idGenerator;

	public AccountServiceManager(IAccountDao accountDao, ITransactionDao traxDao) {
		this.idGenerator = new AccountIdGenerator();
		this.accountService = new AccountService(accountDao, traxDao);
	}
	
	@Override
	public Long createAccount(String name, double openingBalance) throws AccountAlreadyExistException {	
		Long accountId = idGenerator.createID();
		LocalDate openingDate = LocalDate.now();
		Account newAccount = new Account(accountId, name, openingBalance, openingDate);
		return accountService.createAccount(newAccount);
	}
	
	@Override
	public void closeAccount(Long accountNumber) throws AccountNotExistException {
		accountService.closeAccount(accountNumber);
	}
	

}
