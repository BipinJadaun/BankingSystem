package com.iongroup.accountservice.service;

import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;

public interface IAccountValidationService {
	
	public void validateAccount(Long accountNumber) throws AccountNotExistException;

	void validateCreateAccount(Long accountNumber) throws AccountAlreadyExistException;

}
