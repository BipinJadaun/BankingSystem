package com.iongroup.accountservice.service;

import java.time.LocalDate;

import com.iongroup.accountservice.dao.IAccountDao;
import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.accountservice.model.Account;
import com.iongroup.transactionservice.dao.ITransactionDao;
import com.iongroup.transactionservice.model.TransactionType;

public class AccountService implements IAccountService{

	private final IAccountDao accountDao; 
	private final ITransactionDao traxDao;
	private final IAccountValidationService accValidationService;
	private final AccountIdGenerator idGenerator;


	public AccountService(IAccountDao accountDao, ITransactionDao traxDao) {
		this.accountDao = accountDao;
		this.traxDao = traxDao;
		this.accValidationService = new AccountValidationService(accountDao);
		this.idGenerator = new AccountIdGenerator();
	}

	@Override
	public Long createAccount(String name, double openingBalance) throws AccountAlreadyExistException{
		Long accountId = idGenerator.createID();
		accValidationService.validateCreateAccount(accountId);
		
		LocalDate openingDate = LocalDate.now();		
		Account newAccount = new Account(accountId, name, openingBalance, openingDate);
		
		synchronized(newAccount) {
			accountDao.createAccount(newAccount);
			traxDao.addTransection(newAccount.getAccountNo(), TransactionType.DEPOSITE, newAccount.getBalance());
			System.out.println("Account "+ newAccount.getAccountNo()+" created successfully with initial amount "+newAccount.getBalance());
			return accountId;
		}		
	}

	@Override
	public void closeAccount(Long accountNumber) throws AccountNotExistException {

		accValidationService.validateAccount(accountNumber);
		Account userAccount = accountDao.getAccount(accountNumber);
		synchronized(userAccount) {
			accountDao.closeAccount(accountNumber);
			traxDao.removeTransactions(accountNumber);
			System.out.println("Account " + accountNumber + " closed successfully");
		}
	}

	@Override
	public Account getAccount(Long accountNumber) throws AccountNotExistException{
		accValidationService.validateAccount(accountNumber);		
		return accountDao.getAccount(accountNumber);
	}
}

