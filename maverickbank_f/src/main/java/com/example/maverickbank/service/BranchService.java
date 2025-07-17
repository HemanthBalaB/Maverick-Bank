package com.example.maverickbank.service;

import com.example.maverickbank.dto.BranchDTO;
import java.util.List;

public interface BranchService {
    BranchDTO createBranch(BranchDTO branchDTO);
    List<BranchDTO> getAllBranches();
    BranchDTO getBranchById(Long branchId);
    BranchDTO updateBranch(Long branchId, BranchDTO branchDTO);
    void deleteBranch(Long branchId);
}
