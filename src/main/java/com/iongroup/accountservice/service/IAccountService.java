package com.iongroup.accountservice.service;

import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.accountservice.model.Account;

public interface IAccountService {
	
	public Long createAccount(Account acount) throws AccountAlreadyExistException;
	public Account getAccount(Long accountNumber) throws AccountNotExistException;
	public void closeAccount(Long accountNumber) throws AccountNotExistException;	
	
}
