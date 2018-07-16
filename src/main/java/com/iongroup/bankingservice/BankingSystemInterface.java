package com.iongroup.bankingservice;

import com.iongroup.accountservice.endpoint.AccountServiceInterface;
import com.iongroup.transactionservice.endpoint.TransactionServiceInterface;


public interface BankingSystemInterface extends AccountServiceInterface, TransactionServiceInterface{


}
