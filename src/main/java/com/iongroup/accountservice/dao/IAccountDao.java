package com.iongroup.accountservice.dao;

import com.iongroup.accountservice.model.Account;

public interface IAccountDao {
	
	public Account getAccount(Long accountNumber) ;
	
	public void createAccount(Account user);
	
	public void closeAccount(Long accountNumber);

}
