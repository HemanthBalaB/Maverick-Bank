package com.example.maverickbank.repo;

import com.example.maverickbank.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    boolean existsByIfscCode(String ifscCode);
}
