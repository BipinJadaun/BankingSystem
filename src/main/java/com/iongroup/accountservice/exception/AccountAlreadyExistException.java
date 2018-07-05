package com.iongroup.accountservice.exception;

public class AccountAlreadyExistException extends Exception{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7529169288341588924L;

	public AccountAlreadyExistException(String message) {
		super(message);		
	}

}
