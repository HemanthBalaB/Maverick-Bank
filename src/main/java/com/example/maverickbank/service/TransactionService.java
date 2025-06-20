package com.example.maverickbank.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maverickbank.model.AccountHolder;
import com.example.maverickbank.repo.AccountHolderRepository;

@Service
public class TransactionService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;
    
    public String deposit(int accountNo, double amount) {
        AccountHolder accountHolder = accountHolderRepository.findById(accountNo).get();
        if(accountHolder != null) {
            double newBalance = accountHolder.getBalance() + amount;
            accountHolder.setBalance(newBalance);
            accountHolderRepository.save(accountHolder);
            return "Amount " + amount + " deposited successfully. New balance: " + newBalance;
        }
        return "Account not found";
    }
    
    public String withdraw(int accountNo, double amount) {
        AccountHolder accountHolder = accountHolderRepository.findById(accountNo).get();
        if(accountHolder != null) {
            if(accountHolder.getBalance() >= amount) {
                double newBalance = accountHolder.getBalance() - amount;
                accountHolder.setBalance(newBalance);
                accountHolderRepository.save(accountHolder);
                return "Amount " + amount + " withdrawn successfully. New balance: " + newBalance;
            }
            return "Insufficient balance";
        }
        return "Account not found";
    }
    
    public String transferFunds(int fromAccountNo, int toAccountNo, double amount) {
        AccountHolder fromAccount = accountHolderRepository.findById(fromAccountNo).get();
        AccountHolder toAccount = accountHolderRepository.findById(toAccountNo).get();
        
        if(fromAccount == null || toAccount == null) {
            return "One or both accounts not found";
        }
        
        if(fromAccount.getBalance() < amount) {
            return "Insufficient balance in source account";
        }
        
        // Withdraw from source account
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        accountHolderRepository.save(fromAccount);
        
        // Deposit to target account
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountHolderRepository.save(toAccount);
        
        return "Amount " + amount + " transferred successfully from account " + 
               fromAccountNo + " to account " + toAccountNo;
    }
}