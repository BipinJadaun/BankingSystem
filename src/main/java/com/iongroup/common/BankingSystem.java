package com.iongroup.common;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;
import com.iongroup.transactionservice.model.Transaction;


public class BankingSystem {
	
	private final UserEndPoint userEndPoint;
	
	public BankingSystem() {		
		userEndPoint = new UserEndPoint();
	}
	
	public void test() {
		
		Long accountNumber1 = testAccountService("Bipin", 1000);
		
		Long accountNumber2 = testAccountService("Anil", 2000);
		
		testTransections(accountNumber1, accountNumber2, 600, 300, 400);
		
		testConcurrentTransections(accountNumber1, accountNumber2, 700, 250, 400);
		
		testInvalidTransections(accountNumber1, accountNumber2, 1200, 900, 300);
	}


	public Long testAccountService(String user, double initialAmount) {
		
		Long accountNumber = null;
		
		try {
			accountNumber = userEndPoint.createAccount(user, initialAmount);
		} 
		catch (AccountAlreadyExistException e1) {
			System.out.println(e1.getMessage());
		}		
		return accountNumber;
		
	}
	
	public void testTransections(Long accountNumber1, Long accountNumber2, double depositAmount, double withdrawAmount, double transferAmount) {		
		System.out.println("*************************************************************");
		System.out.println("Testing Transactions");
		System.out.println("*************************************************************");
		
		try {
			userEndPoint.deposit(accountNumber1, depositAmount);
			System.out.println("current balance of account "+accountNumber1+" is "+ userEndPoint.getBalance(accountNumber1));
			
			userEndPoint.withdraw(accountNumber1, withdrawAmount);
			System.out.println("current balance of account "+accountNumber1+" is "+ userEndPoint.getBalance(accountNumber1));

			userEndPoint.transfer(accountNumber1, accountNumber2, transferAmount);
			
			System.out.println("current balance of account "+ accountNumber1 +" is "+ userEndPoint.getBalance(accountNumber1));
			System.out.println("current balance of account "+ accountNumber2 +" is "+ userEndPoint.getBalance(accountNumber2));

		} catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}
	}

	public void testConcurrentTransections(Long accountNumber1, Long accountNumber2, double depositAmount, double withdrawAmount, double transferAmount) {
		
		System.out.println("*************************************************************");
		System.out.println("Testing Concurrent Transactions");
		System.out.println("*************************************************************");

		ExecutorService execute = Executors.newFixedThreadPool(3);

		execute.submit(() -> { try {
			userEndPoint.deposit(accountNumber1, depositAmount);
		} 
		catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		} });

		execute.submit(() -> { try {
			userEndPoint.withdraw(accountNumber1, withdrawAmount);
		} 
		catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		} });

		execute.submit(() -> { try {
			userEndPoint.transfer(accountNumber1, accountNumber2, transferAmount);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		} });

		execute.shutdown();

		while (!execute.isTerminated()) {
		}

		try {
			System.out.println("current balance of account "+ accountNumber1 +" is "+ userEndPoint.getBalance(accountNumber1));
			System.out.println("current balance of account "+ accountNumber1 +" is "+ userEndPoint.getBalance(accountNumber2));
			
			List<Transaction> tranxList = userEndPoint.getLatestTrasactions(accountNumber1);
			if (tranxList != null && !tranxList.isEmpty()) {
				System.out.println("Transaction list of account " + accountNumber1);
				for (Transaction tranx : tranxList) {
					System.out.println(tranx.getId() + "  " + tranx.getType() + "  " + tranx.getAmount());
				} 
			}
			
			List<Transaction> tranxList1 = userEndPoint.getLatestTrasactions(accountNumber2);
			if (tranxList1 != null && !tranxList1.isEmpty()) {
				System.out.println("Transaction list of account " + accountNumber2);
				for (Transaction tranx : tranxList1) {
					System.out.println(tranx.getId() + "  " + tranx.getType() + "  " + tranx.getAmount());
				} 
			}
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}		
	}

	public void testInvalidTransections(Long accountNumber1, Long accountNumber2, double depositAmount, double withdrawAmount, double transferAmount) {
		System.out.println("*************************************************************");
		System.out.println("Testing Invalid Transections ");
		System.out.println("*************************************************************");
		try {
			userEndPoint.closeAccount(accountNumber1);
			userEndPoint.closeAccount(accountNumber2);
		} catch (AccountNotExistException e1) {
			System.out.println(e1.getMessage());
		}		

		try {
			userEndPoint.deposit(accountNumber1, depositAmount);
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}

		try {
			userEndPoint.withdraw(accountNumber1, withdrawAmount);
		} catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}

		userEndPoint.transfer(accountNumber1, accountNumber2, transferAmount);

		List<Transaction> tranxList;
		try {
			tranxList = userEndPoint.getLatestTrasactions(accountNumber1);
			for(Transaction tranx : tranxList) {
				System.out.println(tranx.getId() + "  " +tranx.getType() + "  " + tranx.getAmount());
			}
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
