package com.iongroup.accountservice.model;

import java.time.LocalDate;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	
	private final long accountNo;
	private String accountHolderName;
	private double balance;
	private final LocalDate openingDate;
	private final Lock lock;
	
	public Account(Long accountNo, String accountHolderName, double balance, LocalDate openingDate) {		
		this.accountNo = accountNo;
		this.accountHolderName = accountHolderName;
		this.balance = balance;
		this.openingDate = openingDate;
		this.lock = new ReentrantLock();
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public LocalDate getOpeningDate() {
		return openingDate;
	}

	public Lock getLock() {
		return lock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountNo ^ (accountNo >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNo != other.accountNo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", accountHolderName=" + accountHolderName + ", balance=" + balance
				+ ", openingDate=" + openingDate + "]";
	}		
}
