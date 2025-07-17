package com.example.maverickbank.controller;

import com.example.maverickbank.dto.LoanApplicationDTO;
import com.example.maverickbank.model.LoanApplication;
import com.example.maverickbank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "http://localhost:4200")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/apply")
    public String applyLoan(@RequestBody LoanApplicationDTO loanDto) {
        return loanService.applyLoan(loanDto);
    }

    @GetMapping("/status/{accountNo}")
    public List<LoanApplication> getLoanStatus(@PathVariable Long accountNo) {
        return loanService.getLoanStatus(accountNo);
    }

    @GetMapping("/all")
    public List<LoanApplication> getAllLoanApplications() {
        return loanService.getAllLoanApplications();
    }

    @GetMapping("/byStatus/{status}")
    public List<LoanApplication> getLoansByStatus(@PathVariable String status) {
        return loanService.getLoansByStatus(status);
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<String> updateLoanStatusJson(@RequestBody Map<String, String> request) {
        try {
            Long loanId = Long.parseLong(request.get("loanId"));
            Long accountNo = Long.parseLong(request.get("accountNo"));
            String status = request.get("status");

            boolean updated = loanService.updateLoanStatusWithAccount(loanId, status, accountNo);

            return updated
                ? ResponseEntity.ok("Loan status updated successfully")
                : ResponseEntity.badRequest().body("Loan not found or account number mismatch");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<String> payLoanJson(@RequestBody Map<String, Object> request) {
        try {
            Long loanId = Long.parseLong(request.get("loanId").toString());
            Long accountNo = Long.parseLong(request.get("accountNo").toString());
            Double amount = Double.parseDouble(request.get("amount").toString());

            String result = loanService.payLoanAmount(loanId, amount, accountNo);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid payment request: " + e.getMessage());
        }
    }
}
