package com.iongroup.accountservice.service;

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


	public AccountService(IAccountDao accountDao, ITransactionDao traxDao) {
		this.accountDao = accountDao;
		this.traxDao = traxDao;
		this.accValidationService = new AccountValidationService(accountDao);
	}

	@Override
	public Long createAccount(Account userAccount) throws AccountAlreadyExistException{

		accValidationService.validateCreateAccount(userAccount.getAccountNo());
		synchronized(userAccount) {
			accountDao.createAccount(userAccount);
			traxDao.addTransection(userAccount.getAccountNo(), TransactionType.DEPOSITE, userAccount.getBalance());
			System.out.println("Account "+ userAccount.getAccountNo()+" created successfully with initial amount "+userAccount.getBalance());
			return userAccount.getAccountNo();
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

