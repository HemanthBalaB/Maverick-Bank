package com.example.maverickbank.model;

import jakarta.persistence.*;

/**
 * Represents a payee/beneficiary that an account holder can transfer funds to.
 */
@Entity
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beneficiary_id")
    private Long beneficiaryId;

    /**
     * The account number of the owner/user who added this beneficiary.
     */
    @Column(name = "account_no", nullable = false)
    private Long ownerAccountNo;

    /**
     * The beneficiary's account number (destination account).
     */
    @Column(name = "beneficiary_account_no", nullable = false)
    private Long beneficiaryAccountNo;

    /**
     * The name of the beneficiary.
     */
    @Column(name = "beneficiary_name", nullable = false)
    private String beneficiaryName;

    /**
     * Optional nickname for this beneficiary.
     */
    private String nickname;

    /**
     * IFSC code of the beneficiary's bank/branch.
     */
    @Column(name = "ifsc_code")
    private String ifscCode;

    // --- Getters and Setters ---
    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public Long getOwnerAccountNo() {
        return ownerAccountNo;
    }

    public void setOwnerAccountNo(Long ownerAccountNo) {
        this.ownerAccountNo = ownerAccountNo;
    }

    public Long getBeneficiaryAccountNo() {
        return beneficiaryAccountNo;
    }

    public void setBeneficiaryAccountNo(Long beneficiaryAccountNo) {
        this.beneficiaryAccountNo = beneficiaryAccountNo;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
