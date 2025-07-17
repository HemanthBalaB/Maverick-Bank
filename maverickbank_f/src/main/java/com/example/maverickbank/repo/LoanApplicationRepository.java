package com.example.maverickbank.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.maverickbank.model.LoanApplication;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByAccountNo(Long accountNo);  // ✅ FIXED TO Long
    List<LoanApplication> findByStatus(String status);
}
