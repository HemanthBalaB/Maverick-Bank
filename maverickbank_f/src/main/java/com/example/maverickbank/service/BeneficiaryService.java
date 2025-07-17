package com.example.maverickbank.service;

import com.example.maverickbank.dto.BeneficiaryDTO;
import com.example.maverickbank.model.Beneficiary;
import java.util.List;

public interface BeneficiaryService {
    Beneficiary addBeneficiary(BeneficiaryDTO dto);
    List<Beneficiary> getBeneficiaries(Long ownerAccountNo);
    void deleteBeneficiary(Long beneficiaryId);
}
