package com.example.maverickbank.dto;

public class BeneficiaryDTO {
    private Long ownerAccountNo;
    private Long beneficiaryAccountNo;
    private String beneficiaryName;
    private String nickname;
    private String ifscCode;

    // Getters and Setters
    public Long getOwnerAccountNo() { return ownerAccountNo; }
    public void setOwnerAccountNo(Long ownerAccountNo) { this.ownerAccountNo = ownerAccountNo; }

    public Long getBeneficiaryAccountNo() { return beneficiaryAccountNo; }
    public void setBeneficiaryAccountNo(Long beneficiaryAccountNo) { this.beneficiaryAccountNo = beneficiaryAccountNo; }

    public String getBeneficiaryName() { return beneficiaryName; }
    public void setBeneficiaryName(String beneficiaryName) { this.beneficiaryName = beneficiaryName; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
}
