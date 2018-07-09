package com.iongroup.transactionservice.service;

import com.iongroup.accountservice.dao.AccountDao;
import com.iongroup.accountservice.dao.IAccountDao;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.accountservice.model.Account;
import com.iongroup.accountservice.service.AccountValidationService;
import com.iongroup.accountservice.service.IAccountValidationService;
import com.iongroup.transactionservice.dao.ITransactionDao;
import com.iongroup.transactionservice.dao.TransactionDao;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;


public class TransactionManagementService implements ITransactionManagementService{
	
	private final IAccountValidationService accValidationService;
	private final ITransactionValidationService transValidationService;
	private final ITransactionDao transectionDao;
	private final IAccountDao accountDao;

	public TransactionManagementService() {
		this.accValidationService = new AccountValidationService();
		this.transValidationService = new TransactionValidationService();
		this.transectionDao = new TransactionDao();
		this.accountDao = new AccountDao();
	}

	public void deposit(Long accountNumber, double amount) throws AccountNotExistException{
		
		if(accValidationService.isValidAccount(accountNumber)) {
			Account account = accountDao.getAccount(accountNumber);
			synchronized(account) {
				transectionDao.deposit(accountNumber, amount);
			}
			System.out.println("Amount: " + amount +" Deposited to Account - " + accountNumber);
		}else {
			throw new AccountNotExistException("Account "+accountNumber + " does not exist");
		}
	}

	public void withdraw(Long accountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException{

		if(accValidationService.isValidAccount(accountNumber)){
			Account account = accountDao.getAccount(accountNumber);
			synchronized(account) {
				if(transValidationService.isValidTransaction(accountNumber, amount)) {
					transectionDao.withdraw(accountNumber, amount);
				}else {
					throw new InsufficientBalanceException("Account "+accountNumber + " does not have sufficient balance to withdraw amount " +amount);
				}
				System.out.println("Amount: " + amount +" Withdrawn From Account - " + accountNumber);
			}
		}else {
			throw new AccountNotExistException("Account "+accountNumber + " does not exist");
		}
	}
	

	public void transfer(Long fromAccountNumber, Long toAccountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException {
		
		validateTransfer(fromAccountNumber, toAccountNumber, amount);
		Account fromAccount = accountDao.getAccount(fromAccountNumber);
		Account toAccount = accountDao.getAccount(toAccountNumber);
		Account account1, account2;
		
		if(fromAccountNumber < toAccountNumber) {
			account1 = fromAccount;
			account2 = toAccount;
		}else {
			account1 = toAccount;
			account2 = fromAccount;
		}
		
		synchronized(account1) {
			synchronized(account2) {
				transectionDao.withdraw(fromAccountNumber, amount);
				System.out.println("Amount: " + amount +" Withdrawn From Account - " + fromAccountNumber);
				
				transectionDao.deposit(toAccountNumber, amount);
				System.out.println("Amount: " + amount +" Deposited to Account - " + toAccountNumber);
			}
		}		
	}
	
	private void validateTransfer(Long fromAccountNumber, Long toAccountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException  {
		if(!accValidationService.isValidAccount(fromAccountNumber)){
			throw new AccountNotExistException("Account "+fromAccountNumber + " does not exist");		
		}
		if(!transValidationService.isValidTransaction(fromAccountNumber, amount)) {
			throw new InsufficientBalanceException("Account "+fromAccountNumber + " does not have sufficient balance to withdraw amount " +amount);
		}
		if(!accValidationService.isValidAccount(toAccountNumber)){
			throw new AccountNotExistException("Account "+toAccountNumber + " does not exist");			
		}		
	}
}
