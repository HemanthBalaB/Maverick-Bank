package com.example.maverickbank.impl;

import com.example.maverickbank.dto.LoanApplicationDTO;
import com.example.maverickbank.model.AccountHolder;
import com.example.maverickbank.model.LoanApplication;
import com.example.maverickbank.model.TransactionHistory;
import com.example.maverickbank.repo.AccountHolderRepository;
import com.example.maverickbank.repo.LoanApplicationRepository;
import com.example.maverickbank.repo.TransactionHistoryRepository;
import com.example.maverickbank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanApplicationRepository loanRepo;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public String applyLoan(LoanApplicationDTO loanDto) {
        LoanApplication loan = new LoanApplication();
        loan.setAccountNo(loanDto.getAccountNo());
        loan.setLoanAmount(loanDto.getLoanAmount());
        loan.setTenureInMonths(loanDto.getTenureInMonths());
        loan.setPurpose(loanDto.getPurpose());
        loan.setStatus("PENDING");
        loan.setAppliedDate(LocalDate.now());
        loanRepo.save(loan);
        return "Loan application submitted successfully.";
    }

    @Override
    public List<LoanApplication> getLoanStatus(Long accountNo) {
        return loanRepo.findByAccountNo(accountNo);
    }

    @Override
    public List<LoanApplication> getAllLoanApplications() {
        return loanRepo.findAll();
    }

    @Override
    public List<LoanApplication> getLoansByStatus(String status) {
        return loanRepo.findByStatus(status.toUpperCase());
    }

    @Override
    public boolean updateLoanStatus(Long loanId, String status) {
        Optional<LoanApplication> loanOpt = loanRepo.findById(loanId);
        if (loanOpt.isPresent()) {
            LoanApplication loan = loanOpt.get();
            loan.setStatus(status.toUpperCase());
            loanRepo.save(loan);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateLoanStatusWithAccount(Long loanId, String status, Long accountNo) {
        Optional<LoanApplication> loanOpt = loanRepo.findById(loanId);
        if (loanOpt.isPresent()) {
            LoanApplication loan = loanOpt.get();
            if (!loan.getAccountNo().equals(accountNo)) {
                return false;
            }
            loan.setStatus(status.toUpperCase());
            loanRepo.save(loan);
            return true;
        }
        return false;
    }

    @Override
    public String payLoanAmount(Long loanId, Double amount, Long accountNo) {
        LoanApplication loan = loanRepo.findById(loanId)
            .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loan.getAccountNo().equals(accountNo)) {
            return "Account number mismatch with loan";
        }

        AccountHolder holder = accountHolderRepository.findById(accountNo)
            .orElseThrow(() -> new RuntimeException("Account not found"));

        if (holder.getBalance() < amount) return "Insufficient balance";

        holder.setBalance(holder.getBalance() - amount);
        accountHolderRepository.save(holder);

        loan.setStatus("PAID");
        loanRepo.save(loan);

        TransactionHistory txn = new TransactionHistory(
            accountNo, "LOAN_PAYMENT", amount, "Loan payment for loan ID: " + loanId
        );
        transactionHistoryRepository.save(txn);

        return "Loan paid successfully";
    }
}
