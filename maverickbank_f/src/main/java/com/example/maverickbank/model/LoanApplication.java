package com.example.maverickbank.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loan_application")
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "account_no")
    private Long accountNo;

    private Double loanAmount;
    private Integer tenureInMonths;
    private String purpose;
    private String status;

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    // Constructors
    public LoanApplication() {}

    // Getters and Setters
    public Long getLoanId() { return loanId; }
    public void setLoanId(Long loanId) { this.loanId = loanId; }

    public Long getAccountNo() { return accountNo; }
    public void setAccountNo(Long accountNo) { this.accountNo = accountNo; }

    public Double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Double loanAmount) { this.loanAmount = loanAmount; }

    public Integer getTenureInMonths() { return tenureInMonths; }
    public void setTenureInMonths(Integer tenureInMonths) { this.tenureInMonths = tenureInMonths; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }
}
