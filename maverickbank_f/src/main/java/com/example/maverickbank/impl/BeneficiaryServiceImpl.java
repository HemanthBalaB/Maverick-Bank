package com.example.maverickbank.impl;

import com.example.maverickbank.dto.BeneficiaryDTO;
import com.example.maverickbank.model.Beneficiary;
import com.example.maverickbank.repo.BeneficiaryRepository;
import com.example.maverickbank.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Override
    public Beneficiary addBeneficiary(BeneficiaryDTO dto) {
        Beneficiary ben = new Beneficiary();
        ben.setOwnerAccountNo(dto.getOwnerAccountNo());
        ben.setBeneficiaryAccountNo(dto.getBeneficiaryAccountNo());
        ben.setBeneficiaryName(dto.getBeneficiaryName());
        ben.setNickname(dto.getNickname());
        ben.setIfscCode(dto.getIfscCode());
        return beneficiaryRepository.save(ben);
    }

    @Override
    public List<Beneficiary> getBeneficiaries(Long ownerAccountNo) {
        return beneficiaryRepository.findByOwnerAccountNo(ownerAccountNo);
    }

    @Override
    public void deleteBeneficiary(Long beneficiaryId) {
        beneficiaryRepository.deleteById(beneficiaryId);
    }
}
