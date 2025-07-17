package com.example.maverickbank.service;

import com.example.maverickbank.model.TransactionHistory;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface TransactionService {
    String deposit(Long accountNo, double amount);
    String withdraw(Long accountNo, double amount);
    String transferFunds(Long fromAccountNo, Long toAccountNo, double amount);
    TransactionHistory depositWithReceipt(Long accountNo, double amount);
    String phonePeTransfer(Long fromAccount, Long toAccount, Double amount);
    List<TransactionHistory> getMiniStatement(Long accountNo);

    // ✅ New method for PDF export
    ByteArrayInputStream buildPdfStatement(Long accountNo);
}
