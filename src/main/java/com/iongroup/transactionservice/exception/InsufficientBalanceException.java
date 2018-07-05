package com.iongroup.transactionservice.exception;

public class InsufficientBalanceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5354213069145915616L;

	public InsufficientBalanceException(String message) {
		super(message);
	}
}
