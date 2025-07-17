package com.example.maverickbank.impl;

import com.example.maverickbank.model.AccountHolder;
import com.example.maverickbank.model.TransactionHistory;
import com.example.maverickbank.repo.AccountHolderRepository;
import com.example.maverickbank.repo.TransactionHistoryRepository;
import com.example.maverickbank.service.TransactionService;
import com.example.maverickbank.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private PDFGenerator pdfGenerator;

    @Override
    public String deposit(Long accountNo, double amount) {
        AccountHolder accountHolder = accountHolderRepository.findById(accountNo).orElse(null);
        if (accountHolder != null) {
            double newBalance = accountHolder.getBalance() + amount;
            accountHolder.setBalance(newBalance);
            accountHolderRepository.save(accountHolder);

            transactionHistoryRepository.save(
                new TransactionHistory(accountNo, "DEPOSIT", amount,
                        "Deposited ₹" + amount + ". New balance: ₹" + newBalance)
            );
            return "Amount ₹" + amount + " deposited. New balance: ₹" + newBalance;
        }
        return "❌ Account not found";
    }

    @Override
    public String withdraw(Long accountNo, double amount) {
        AccountHolder accountHolder = accountHolderRepository.findById(accountNo).orElse(null);
        if (accountHolder != null) {
            if (accountHolder.getBalance() >= amount) {
                double newBalance = accountHolder.getBalance() - amount;
                accountHolder.setBalance(newBalance);
                accountHolderRepository.save(accountHolder);

                transactionHistoryRepository.save(
                    new TransactionHistory(accountNo, "WITHDRAW", amount,
                            "Withdrawn ₹" + amount + ". New balance: ₹" + newBalance)
                );

                return "Amount ₹" + amount + " withdrawn. New balance: ₹" + newBalance;
            }
            return "❌ Insufficient balance";
        }
        return "❌ Account not found";
    }

    @Override
    public String transferFunds(Long fromAccountNo, Long toAccountNo, double amount) {
        AccountHolder fromAccount = accountHolderRepository.findById(fromAccountNo).orElse(null);
        AccountHolder toAccount = accountHolderRepository.findById(toAccountNo).orElse(null);

        if (fromAccount == null || toAccount == null) {
            return "❌ One or both accounts not found";
        }

        if (fromAccount.getBalance() < amount) {
            return "❌ Insufficient balance";
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountHolderRepository.save(fromAccount);
        accountHolderRepository.save(toAccount);

        transactionHistoryRepository.save(
            new TransactionHistory(fromAccountNo, "TRANSFER", amount, "Transferred to Acc " + toAccountNo)
        );
        transactionHistoryRepository.save(
            new TransactionHistory(toAccountNo, "TRANSFER", amount, "Received from Acc " + fromAccountNo)
        );

        return "✅ ₹" + amount + " transferred from Acc " + fromAccountNo + " to Acc " + toAccountNo;
    }

    @Override
    public TransactionHistory depositWithReceipt(Long accountNo, double amount) {
        Optional<AccountHolder> optional = accountHolderRepository.findById(accountNo);
        if (optional.isEmpty()) return null;

        AccountHolder account = optional.get();
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        accountHolderRepository.save(account);

        TransactionHistory txn = new TransactionHistory(accountNo, "DEPOSIT", amount,
                "Deposited ₹" + amount + ". New balance: ₹" + newBalance);
        txn.setTimestamp(LocalDateTime.now());

        return transactionHistoryRepository.save(txn);
    }

    @Override
    public String phonePeTransfer(Long fromAccount, Long toAccount, Double amount) {
        AccountHolder sender = accountHolderRepository.findById(fromAccount)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        AccountHolder receiver = accountHolderRepository.findById(toAccount)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance() < amount) return "❌ Insufficient Balance";

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountHolderRepository.save(sender);
        accountHolderRepository.save(receiver);

        transactionHistoryRepository.save(
            new TransactionHistory(fromAccount, "PHONEPE", amount, "Sent to Acc " + toAccount)
        );
        transactionHistoryRepository.save(
            new TransactionHistory(toAccount, "PHONEPE", amount, "Received from Acc " + fromAccount)
        );

        return "✅ ₹" + amount + " transferred via PhonePe";
    }

    @Override
    public List<TransactionHistory> getMiniStatement(Long accountNo) {
        return transactionHistoryRepository.findTop10ByAccountNoOrderByTimestampDesc(accountNo);
    }

    @Override
    public ByteArrayInputStream buildPdfStatement(Long accountNo) {
        AccountHolder account = accountHolderRepository.findById(accountNo)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        List<TransactionHistory> transactions = transactionHistoryRepository.findByAccountNo(accountNo);
        return pdfGenerator.generatePdf(account, transactions);
    }
}
