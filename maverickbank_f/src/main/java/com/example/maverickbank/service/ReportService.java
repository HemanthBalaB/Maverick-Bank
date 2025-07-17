package com.example.maverickbank.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maverickbank.model.AccountHolder;
import com.example.maverickbank.model.TransactionHistory;
import com.example.maverickbank.repo.AccountHolderRepository;
import com.example.maverickbank.repo.TransactionHistoryRepository;

@Service
public class ReportService {

    @Autowired
    private AccountHolderRepository accountRepo;

    @Autowired
    private TransactionHistoryRepository txnRepo;

    public Optional<AccountHolder> getAccountStatement(Long accountNo) {
        return accountRepo.findById(accountNo);
    }

    public List<TransactionHistory> getTransactionHistory(Long accountNo) {
        return txnRepo.findByAccountNo(accountNo);
    }

    public List<TransactionHistory> getAllTransactions() {
        return txnRepo.findAll();
    }

    public List<TransactionHistory> filterTransactions(Long accountNo, String type,
                                                       LocalDate fromDate, LocalDate toDate) {
        return txnRepo.findByAccountNo(accountNo).stream()
                .filter(txn -> (type == null || txn.getType().equalsIgnoreCase(type)) &&
                               (fromDate == null || !txn.getTimestamp().toLocalDate().isBefore(fromDate)) &&
                               (toDate == null || !txn.getTimestamp().toLocalDate().isAfter(toDate)))
                .collect(Collectors.toList());
    }
}
