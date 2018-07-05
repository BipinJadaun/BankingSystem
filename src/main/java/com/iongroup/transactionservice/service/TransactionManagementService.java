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
					throw new AccountNotExistException("Account "+accountNumber + " does not have sufficient balance for withdraw amount " +amount);
				}
				System.out.println("Amount: " + amount +" Withdrawn From Account - " + accountNumber);
			}
		}else {
			throw new AccountNotExistException("Account "+accountNumber + " does not exist");
		}
	}
	

	public void transfer(Long fromAccountNumber, Long toAccountNumber, double amount) {
		
		if(accValidationService.isValidAccount(fromAccountNumber)){
			Account account = accountDao.getAccount(fromAccountNumber);
		
			synchronized(account) {
				try {
					withdraw(fromAccountNumber, amount);
				} catch (AccountNotExistException | InsufficientBalanceException e) {
					System.out.println(e.getMessage());
					return;
				}
				try {
					deposit(toAccountNumber, amount);
				} catch (AccountNotExistException e) {
					System.out.println(e.getMessage());
					try {
						deposit(fromAccountNumber, amount);
					} catch (AccountNotExistException e1) {
						System.out.println("Transection Failed");
					}
				}
			}
		}
	}	
}
