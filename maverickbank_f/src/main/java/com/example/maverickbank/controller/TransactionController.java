package com.example.maverickbank.controller;

import com.example.maverickbank.model.TransactionHistory;
import com.example.maverickbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /* --------- core money-movement --------- */

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody Map<String,Object> body) {
        return ResponseEntity.ok(
                transactionService.deposit(
                        Long.valueOf(body.get("accountNo").toString()),
                        Double.parseDouble(body.get("amount").toString()))
        );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody Map<String,Object> body) {
        return ResponseEntity.ok(
                transactionService.withdraw(
                        Long.valueOf(body.get("accountNo").toString()),
                        Double.parseDouble(body.get("amount").toString()))
        );
    }

    @PostMapping("/transferFunds")
    public ResponseEntity<String> transferFunds(@RequestBody Map<String,Object> body) {
        return ResponseEntity.ok(
                transactionService.transferFunds(
                        Long.valueOf(body.get("fromAccountNo").toString()),
                        Long.valueOf(body.get("toAccountNo").toString()),
                        Double.parseDouble(body.get("amount").toString()))
        );
    }

    /* --------- receipt / PhonePe --------- */

    @PostMapping("/depositWithReceipt")
    public TransactionHistory depositWithReceipt(@RequestBody Map<String,Object> body) {
        return transactionService.depositWithReceipt(
                Long.valueOf(body.get("accountNo").toString()),
                Double.parseDouble(body.get("amount").toString())
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> phonePe(@RequestBody Map<String,Object> body) {
        return ResponseEntity.ok(
                transactionService.phonePeTransfer(
                        Long.valueOf(body.get("fromAccountNo").toString()),
                        Long.valueOf(body.get("toAccountNo").toString()),
                        Double.parseDouble(body.get("amount").toString()))
        );
    }

    /* --------- mini-statement --------- */

    @GetMapping("/recent/{accountNo}")          // dashboard (last 10)
    public List<TransactionHistory> recent(@PathVariable Long accountNo) {
        return transactionService.getMiniStatement(accountNo);
    }

    @GetMapping("/miniStatement/{accountNo}")   // full (reuse same method for now)
    public List<TransactionHistory> mini(@PathVariable Long accountNo) {
        return transactionService.getMiniStatement(accountNo);
    }

    /* --------- PDF download --------- */

    @GetMapping(value = "/statement/{accountNo}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> statementPdf(@PathVariable Long accountNo) {

        ByteArrayInputStream pdf = transactionService.buildPdfStatement(accountNo);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=statement_" + accountNo + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.readAllBytes());
    }

}
