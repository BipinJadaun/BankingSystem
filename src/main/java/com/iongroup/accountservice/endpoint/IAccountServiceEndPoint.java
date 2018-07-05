package com.iongroup.accountservice.endpoint;

import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;

public interface IAccountServiceEndPoint {
	
	public Long createAccount(String name, double openingBalance) throws AccountAlreadyExistException ; 
	
	public void closeAccount(Long accountNumber) throws AccountNotExistException;

}
