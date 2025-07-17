package com.example.maverickbank.service;

import com.example.maverickbank.dto.LoanApplicationDTO;
import com.example.maverickbank.model.LoanApplication;
import java.util.List;

public interface LoanService {
    String applyLoan(LoanApplicationDTO loanDto);
    List<LoanApplication> getLoanStatus(Long accountNo);
    List<LoanApplication> getAllLoanApplications();
    List<LoanApplication> getLoansByStatus(String status);
    boolean updateLoanStatus(Long loanId, String status);
    boolean updateLoanStatusWithAccount(Long loanId, String status, Long accountNo);
    String payLoanAmount(Long loanId, Double amount, Long accountNo);
}
