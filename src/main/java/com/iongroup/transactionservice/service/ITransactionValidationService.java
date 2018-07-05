package com.iongroup.transactionservice.service;

import java.util.Date;

import com.iongroup.accountservice.model.Account;

public interface ITransactionValidationService {
	
	public boolean isValidTransaction(Long accountNumber, double amount);

	public boolean isValidIntarval(Account account, Date startDate, Date endDate);

}
