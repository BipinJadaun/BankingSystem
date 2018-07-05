package com.iongroup;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.BeforeClass;
import org.junit.Test;

import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.common.UserEndPoint;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;
import com.iongroup.transactionservice.model.Transaction;


public class TestClass {

	private static UserEndPoint userEndPoint;
	static Long accountNumber1 = null;
	static Long accountNumber2 = null;

	@BeforeClass
	public static void setUp() {
		userEndPoint = new UserEndPoint();		
	}

	@Test
	public void testAccountCreate() {
		try {
			accountNumber1 = userEndPoint.createAccount("Bipin", 1000);			
		} 
		catch (AccountAlreadyExistException e1) {
			System.out.println(e1.getMessage());
		}
	}

	@Test
	public void testDeposit() {
		try {
			testAccountCreate();
			userEndPoint.deposit(accountNumber1, 500);
			System.out.println(userEndPoint.getBalance(accountNumber1));
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void testWithdraw() {

		try {
			testAccountCreate();
			userEndPoint.withdraw(accountNumber1, 500);
			System.out.println(userEndPoint.getBalance(accountNumber1));
		} catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testWithdrawWithInsufficientBalance() {
		try {
			testAccountCreate();
			userEndPoint.withdraw(accountNumber1, 5000);
			System.out.println(userEndPoint.getBalance(accountNumber1));
		} catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testBalance() {
		try {
			testAccountCreate();
			double balance = userEndPoint.getBalance(accountNumber1);
			System.out.println(balance);
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void testTransfer() {
		try {
			testAccountCreate();
			testAccountCreate2();
			userEndPoint.transfer(accountNumber1, accountNumber2, 500);
			System.out.println(userEndPoint.getBalance(accountNumber1));
			System.out.println(userEndPoint.getBalance(accountNumber2));
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}		
	}

	@Test
	public void testStatement() {

		try {
			testAccountCreate();
			userEndPoint.deposit(accountNumber1, 1000);
			userEndPoint.withdraw(accountNumber1, 500);
			List<Transaction> tranxList = userEndPoint.getLatestTrasactions(accountNumber1);

			assertTrue(tranxList != null);

			for(Transaction tranx : tranxList) {
				System.out.println(tranx.getId() + "  " +tranx.getType() + "  " + tranx.getAmount());
			}
		} catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}		
	}

	@Test
	public void testConcurrentTransections() {

		ExecutorService execute = Executors.newFixedThreadPool(3);
		
		testAccountCreate();
		testAccountCreate2();
		
		execute.submit(() -> { try {
			userEndPoint.deposit(accountNumber1, 1000);
		} 
		catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		} });

		execute.submit(() -> { try {
			userEndPoint.withdraw(accountNumber1, 500);
		} 
		catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		} });

		execute.submit(() -> { try {
			userEndPoint.transfer(accountNumber1, accountNumber2, 200);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		} });

		execute.shutdown();
		while (!execute.isTerminated()) {
		}

		try {
			System.out.println(userEndPoint.getBalance(accountNumber1));
			System.out.println(userEndPoint.getBalance(accountNumber2));
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void testAccountCreate2() {
		try {
			accountNumber2 = userEndPoint.createAccount("Anil", 2000);			
		} 
		catch (AccountAlreadyExistException e1) {
			System.out.println(e1.getMessage());
		}
	}
	
}

