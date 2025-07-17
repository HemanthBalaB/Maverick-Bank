package com.example.maverickbank.controller;

import com.example.maverickbank.dto.BeneficiaryDTO;
import com.example.maverickbank.model.Beneficiary;
import com.example.maverickbank.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    // Add beneficiary (USER/EMPLOYEE/ADMIN)
    @PostMapping
    public ResponseEntity<Beneficiary> addBeneficiary(@RequestBody BeneficiaryDTO dto) {
        Beneficiary saved = beneficiaryService.addBeneficiary(dto);
        return ResponseEntity.ok(saved);
    }

    // List all beneficiaries for a user
    @GetMapping("/{ownerAccountNo}")
    public ResponseEntity<List<Beneficiary>> getBeneficiaries(@PathVariable Long ownerAccountNo) {
        List<Beneficiary> list = beneficiaryService.getBeneficiaries(ownerAccountNo);
        return ResponseEntity.ok(list);
    }

    // Delete beneficiary (optional)
    @DeleteMapping("/{beneficiaryId}")
    public ResponseEntity<?> deleteBeneficiary(@PathVariable Long beneficiaryId) {
        beneficiaryService.deleteBeneficiary(beneficiaryId);
        return ResponseEntity.ok("Beneficiary removed successfully.");
    }
}
