package com.example.maverickbank.service;

import com.example.maverickbank.dto.AccountHolderDTO;
import com.example.maverickbank.model.AccountHolder;
import com.example.maverickbank.model.Branch;
import com.example.maverickbank.repo.AccountHolderRepository;
import com.example.maverickbank.repo.BranchRepository;
import com.example.maverickbank.repo.LoanApplicationRepository;
import com.example.maverickbank.repo.TransactionHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountHolderService {

    @Autowired private AccountHolderRepository accountHolderRepository;
    @Autowired private BranchRepository branchRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private LoanApplicationRepository loanApplicationRepository;
    @Autowired private TransactionHistoryRepository transactionHistoryRepository;

    public AccountHolder createAccount(AccountHolderDTO dto) {
        AccountHolder ah = new AccountHolder();
        ah.setName(dto.getName());
        ah.setEmail(dto.getEmail());
        ah.setMobileNo(dto.getMobileNo());
        ah.setPassword(passwordEncoder.encode(dto.getPassword()));
        ah.setBalance(dto.getBalance());
        ah.setAccountType(dto.getAccountType());
        ah.setAddress(dto.getAddress());

        // IFSC: use provided or generate new
        ah.setIfscCode((dto.getIfscCode() != null && !dto.getIfscCode().isBlank())
            ? dto.getIfscCode()
            : generateRandomIfsc()
        );

        // Link Branch by ID
        if (dto.getBranchId() != null) {
            Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
            ah.setBranch(branch);
        }

        // Additional personal fields
        ah.setGender(dto.getGender());
        if (dto.getDob() != null && !dto.getDob().isBlank()) {
            ah.setDob(LocalDate.parse(dto.getDob()));
        }
        ah.setAadhar(dto.getAadhar());
        ah.setPan(dto.getPan());

        return accountHolderRepository.save(ah);
    }

    public AccountHolder searchAccount(Long accountNo) {
        return accountHolderRepository.findById(accountNo)
            .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public AccountHolder updateAccount(AccountHolderDTO dto) {
        AccountHolder ah = searchAccount(dto.getAccountNo());

        // Update allowed fields
        ah.setName(dto.getName());
        ah.setEmail(dto.getEmail());
        ah.setMobileNo(dto.getMobileNo());
        ah.setAddress(dto.getAddress());

        // Removed: ah.setAccountType(dto.getAccountType());

        return accountHolderRepository.save(ah);
    }

    @Transactional
    public boolean closeAccount(Long accountNo) {
        Optional<AccountHolder> opt = accountHolderRepository.findById(accountNo);
        if (opt.isPresent()) {
            // Delete all related loan applications
            loanApplicationRepository.deleteAll(
                loanApplicationRepository.findByAccountNo(accountNo)
            );

            // Delete all transaction history
            transactionHistoryRepository.deleteAll(
                transactionHistoryRepository.findByAccountNo(accountNo)
            );

            // Now delete account holder
            accountHolderRepository.deleteById(accountNo);
            return true;
        }
        return false;
    }

    public AccountHolder login(Long accountNo, String password) {
        AccountHolder ah = searchAccount(accountNo);
        if (!passwordEncoder.matches(password, ah.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return ah;
    }

    public AccountHolder searchByMobileNo(String mobileNo) {
        return accountHolderRepository.findByMobileNo(mobileNo);
    }

    public java.util.List<AccountHolder> getAllAccounts() {
        return accountHolderRepository.findAll();
    }

    private String generateRandomIfsc() {
        return "MAVB" + String.format("%08d", new Random().nextInt(100_000_000));
    }
}
