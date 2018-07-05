package com.iongroup.accountservice.service;

import com.iongroup.accountservice.dao.AccountDao;
import com.iongroup.accountservice.dao.IAccountDao;
import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.accountservice.model.Account;
import com.iongroup.transactionservice.dao.TransactionDao;
import com.iongroup.transactionservice.model.TransactionType;

public class AccountService implements IAccountService{

	private final IAccountDao accountDao; 
	private final TransactionDao traxDao;
	private final IAccountValidationService validationService;


	public AccountService() {
		this.accountDao = new AccountDao();
		this.traxDao = new TransactionDao();
		this.validationService = new AccountValidationService();
	}

	@Override
	public Long createAccount(Account userAccount) throws AccountAlreadyExistException{

		if(!validationService.isValidAccount(userAccount.getAccountNo())) {
			synchronized(userAccount) {
				accountDao.createAccount(userAccount);
				traxDao.addTransection(userAccount.getAccountNo(), TransactionType.DEPOSITE, userAccount.getBalance());
				System.out.println("Account "+ userAccount.getAccountNo()+" created successfully with initial amount "+userAccount.getBalance());
				return userAccount.getAccountNo();
			}

		}else {
			throw new AccountAlreadyExistException("Account "+userAccount.getAccountNo() + " already exist");
		}		
	}

	@Override
	public void closeAccount(Long accountNumber) throws AccountNotExistException {

		if(validationService.isValidAccount(accountNumber)) {
			Account userAccount = accountDao.getAccount(accountNumber);
			synchronized(userAccount) {
				accountDao.closeAccount(accountNumber);
				traxDao.removeTransactions(accountNumber);
				System.out.println("Account " + accountNumber + " closed successfully");
			}
		}else {
			throw new AccountNotExistException("Account "+accountNumber + " does not exist");
		}
	}

	@Override
	public Account getAccount(Long accountNumber) throws AccountNotExistException{
		if(validationService.isValidAccount(accountNumber)) {
			return accountDao.getAccount(accountNumber);
		}
		throw new AccountNotExistException("Account "+ accountNumber + " does not exist");
	}
}

