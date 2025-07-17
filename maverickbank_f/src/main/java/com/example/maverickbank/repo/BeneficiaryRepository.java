package com.example.maverickbank.repo;

import com.example.maverickbank.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    List<Beneficiary> findByOwnerAccountNo(Long ownerAccountNo);
}
