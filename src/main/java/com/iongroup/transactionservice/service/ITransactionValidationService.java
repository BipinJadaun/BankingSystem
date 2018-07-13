package com.iongroup.transactionservice.service;

import java.time.LocalDate;

import com.iongroup.transactionservice.exception.InsufficientBalanceException;
import com.iongroup.transactionservice.exception.InvalidTimeIntarvalException;

public interface ITransactionValidationService {
	
	public void validateTransaction(Long accountNumber, double amount) throws InsufficientBalanceException;
	
	public void validateTimeIntarval(LocalDate fromDate, LocalDate toDate) throws InvalidTimeIntarvalException;

}
