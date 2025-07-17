package com.example.maverickbank.impl;

import com.example.maverickbank.dto.BranchDTO;
import com.example.maverickbank.model.Branch;
import com.example.maverickbank.repo.BranchRepository;
import com.example.maverickbank.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    private BranchDTO convertToDTO(Branch branch) {
        BranchDTO dto = new BranchDTO();
        dto.setBranchId(branch.getBranchId());
        dto.setBranchName(branch.getBranchName());
        dto.setIfscCode(branch.getIfscCode());
        dto.setAddress(branch.getAddress());
        return dto;
    }

    private Branch convertToEntity(BranchDTO dto) {
        Branch branch = new Branch();
        branch.setBranchId(dto.getBranchId());
        branch.setBranchName(dto.getBranchName());
        branch.setIfscCode(dto.getIfscCode());
        branch.setAddress(dto.getAddress());
        return branch;
    }

    @Override
    public BranchDTO createBranch(BranchDTO branchDTO) {
        if (branchRepository.existsByIfscCode(branchDTO.getIfscCode())) {
            throw new RuntimeException("IFSC code already exists!");
        }
        Branch saved = branchRepository.save(convertToEntity(branchDTO));
        return convertToDTO(saved);
    }

    @Override
    public List<BranchDTO> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BranchDTO getBranchById(Long branchId) {
        return branchRepository.findById(branchId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
    }

    @Override
    public BranchDTO updateBranch(Long branchId, BranchDTO branchDTO) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        branch.setBranchName(branchDTO.getBranchName());
        branch.setIfscCode(branchDTO.getIfscCode());
        branch.setAddress(branchDTO.getAddress());
        Branch updated = branchRepository.save(branch);
        return convertToDTO(updated);
    }

    @Override
    public void deleteBranch(Long branchId) {
        if (!branchRepository.existsById(branchId)) {
            throw new RuntimeException("Branch not found");
        }
        branchRepository.deleteById(branchId);
    }
}
