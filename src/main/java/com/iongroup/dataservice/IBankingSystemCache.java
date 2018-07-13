package com.iongroup.dataservice;

import java.util.List;

import com.iongroup.accountservice.model.Account;
import com.iongroup.transactionservice.model.Transaction;

public interface IBankingSystemCache {
	
	public void AddAccount(long accountNo, Account user);
	
	public Account getAccount(Long accountNumber);

	public void deleteAccount(Long accountNumber);

	public void addTransection(long accountNo, List<Transaction> traxList);
	
	public List<Transaction> getTransactions(Long accountNumber); 

	public void deleteTransactions(Long accountNumber); 
}
