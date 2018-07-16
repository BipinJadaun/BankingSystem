package com.iongroup.transactionservice.service;

import java.time.LocalDate;

import com.iongroup.accountservice.dao.IAccountDao;
import com.iongroup.accountservice.model.Account;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;
import com.iongroup.transactionservice.exception.InvalidDateException;

public class TransactionValidationService implements ITransactionValidationService{

	private IAccountDao accountDao;

	public TransactionValidationService(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public void validateTransaction(Long accountNumber, double amount) throws InsufficientBalanceException {
		Account account = accountDao.getAccount(accountNumber);
		if(account.getBalance() < amount) {
			throw new InsufficientBalanceException("Account "+accountNumber + " does not have sufficient balance to withdraw amount " +amount);
		}
	}

	@Override
	public void validateTimeIntarval(LocalDate fromDate, LocalDate toDate) throws InvalidDateException {
		if(toDate.isBefore(fromDate))
			throw new InvalidDateException("ToDate "+ toDate +" must be equal or greater than FromDate "+ fromDate);
		if(toDate.isAfter(LocalDate.now()))
			throw new InvalidDateException("ToDate "+ toDate +" must be equal or less than Today " + LocalDate.now());
	}
	
	
}
