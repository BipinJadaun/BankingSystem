package com.iongroup.transactionservice.service;

import java.util.Date;

import com.iongroup.accountservice.dao.AccountDao;
import com.iongroup.accountservice.dao.IAccountDao;
import com.iongroup.accountservice.model.Account;

public class TransactionValidationService implements ITransactionValidationService{
	
	private IAccountDao accountDao;

	public TransactionValidationService() {
		this.accountDao = new AccountDao();
	}

	@Override
	public boolean isValidTransaction(Long accountNumber, double amount) {
		return isTransectionAllowed(accountNumber, amount);
	}
	
	private boolean isTransectionAllowed(Long accountNumber, double amount) {
		Account account = accountDao.getAccount(accountNumber);
			if(account.getBalance() > amount) {
				return true;
			}
		return false;
	}

	@Override
	public boolean isValidIntarval(Account account, Date startDate, Date endDate) {
		Date today = new Date();
		if(startDate.compareTo(account.getOpeningDate())> 0 && endDate.compareTo(today) < 0) {
			return true;
		}
		return false;
	}
}
