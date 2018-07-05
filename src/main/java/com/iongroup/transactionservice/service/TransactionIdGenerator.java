package com.iongroup.transactionservice.service;

import java.util.concurrent.atomic.AtomicLong;

public class TransactionIdGenerator {
	
	private AtomicLong idCounter = new AtomicLong();
	private String transactionID = "AA";
	
	public String createID() {		
			 return transactionID + idCounter.getAndIncrement();		
	}
}
