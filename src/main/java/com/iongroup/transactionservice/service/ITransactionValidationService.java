package com.iongroup.transactionservice.service;

public interface ITransactionValidationService {
	
	public boolean isValidTransaction(Long accountNumber, double amount);

}
