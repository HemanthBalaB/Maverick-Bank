package com.example.maverickbank.dto;

import lombok.Data;

@Data
public class BranchDTO {
    private Long branchId;
    private String branchName;
    private String ifscCode;
    private String address;
}
