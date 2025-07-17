package com.example.maverickbank.controller;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.maverickbank.model.AccountHolder;
import com.example.maverickbank.model.TransactionHistory;
import com.example.maverickbank.repo.AccountHolderRepository;
import com.example.maverickbank.repo.TransactionHistoryRepository;
import com.example.maverickbank.util.PDFGenerator;

@RestController
public class ReportController {

    @Autowired
    private AccountHolderRepository accountRepo;

    @Autowired
    private TransactionHistoryRepository txnRepo;

    @Autowired
    private PDFGenerator pdfGenerator;

    // ✅ 1. Account Statement
    @GetMapping("/accountStatement/{accountNo}")
    public AccountHolder getAccountStatement(@PathVariable Long accountNo) {
        return accountRepo.findById(accountNo).orElse(null);
    }

    // ✅ 2. Transaction History
    @GetMapping("/transactionHistory/{accountNo}")
    public List<TransactionHistory> getTransactionHistory(@PathVariable Long accountNo) {
        return txnRepo.findByAccountNo(accountNo);
    }

    // ✅ 3. All Transactions (Admin View)
    @GetMapping("/allTransactions")
    public List<TransactionHistory> getAllTransactions() {
        return txnRepo.findAll();
    }

    // ✅ 4. PDF Export of Statement
    @GetMapping("/exportStatement/{accountNo}")
    public ResponseEntity<InputStreamResource> exportStatement(@PathVariable Long accountNo) {
        AccountHolder account = accountRepo.findById(accountNo).orElse(null);
        List<TransactionHistory> transactions = txnRepo.findByAccountNo(accountNo);
        ByteArrayInputStream pdfStream = pdfGenerator.generatePdf(account, transactions);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=statement_" + accountNo + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    // ✅ 5. Filter Transactions by Type/Date (GET with query params)
    @GetMapping("/transactionFilter/{accountNo}")
    public List<TransactionHistory> filterTransactions(
            @PathVariable Long accountNo,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {

        List<TransactionHistory> transactions = txnRepo.findByAccountNo(accountNo);

        return transactions.stream()
                .filter(txn -> type == null || txn.getType().equalsIgnoreCase(type))
                .filter(txn -> {
                    if (from == null) return true;
                    LocalDate fromDate = LocalDate.parse(from);
                    return !txn.getTimestamp().toLocalDate().isBefore(fromDate);
                })
                .filter(txn -> {
                    if (to == null) return true;
                    LocalDate toDate = LocalDate.parse(to);
                    return !txn.getTimestamp().toLocalDate().isAfter(toDate);
                })
                .toList();
    }

    // ✅ 6. Filter Transactions using JSON body
    @PostMapping("/filterTransactions")
    public List<TransactionHistory> filterTransactionsJson(@RequestBody Map<String, String> filters) {
        Long accountNo = Long.parseLong(filters.get("accountNo"));
        String type = filters.getOrDefault("type", null);
        String fromDateStr = filters.get("from");
        String toDateStr = filters.get("to");

        List<TransactionHistory> transactions = txnRepo.findByAccountNo(accountNo);

        return transactions.stream()
                .filter(txn -> type == null || txn.getType().equalsIgnoreCase(type))
                .filter(txn -> {
                    if (fromDateStr == null) return true;
                    LocalDate fromDate = LocalDate.parse(fromDateStr);
                    return !txn.getTimestamp().toLocalDate().isBefore(fromDate);
                })
                .filter(txn -> {
                    if (toDateStr == null) return true;
                    LocalDate toDate = LocalDate.parse(toDateStr);
                    return !txn.getTimestamp().toLocalDate().isAfter(toDate);
                })
                .toList();
    }
}
