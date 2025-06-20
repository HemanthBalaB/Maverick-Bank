package com.example.maverickbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.maverickbank.service.TransactionService;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    
    @PostMapping("/deposit/{accountNo}")
    public String deposit(@PathVariable int accountNo, @RequestParam double amount) {
        return transactionService.deposit(accountNo, amount);
    }
    
    @PostMapping("/withdraw/{accountNo}")
    public String withdraw(@PathVariable int accountNo, @RequestParam double amount) {
        return transactionService.withdraw(accountNo, amount);
    }
    
    @PostMapping("/transferFunds")
    public String transferFunds(
            @RequestParam int fromAccountNo, 
            @RequestParam int toAccountNo, 
            @RequestParam double amount) {
        return transactionService.transferFunds(fromAccountNo, toAccountNo, amount);
    }
}