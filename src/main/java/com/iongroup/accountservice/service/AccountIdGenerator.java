package com.iongroup.accountservice.service;

import java.util.concurrent.atomic.AtomicLong;

public class AccountIdGenerator {
	
	private AtomicLong idCounter = new AtomicLong(1111);
	
	public Long createID() {	

			 return idCounter.getAndIncrement();	
	}
}
