package com.example.maverickbank.dto;

public class LoanApplicationDTO {
    private Long accountNo;
    private Double loanAmount;
    private Integer tenureInMonths;
    private String purpose;

    // Getters and Setters
    public Long getAccountNo() { return accountNo; }
    public void setAccountNo(Long accountNo) { this.accountNo = accountNo; }

    public Double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Double loanAmount) { this.loanAmount = loanAmount; }

    public Integer getTenureInMonths() { return tenureInMonths; }
    public void setTenureInMonths(Integer tenureInMonths) { this.tenureInMonths = tenureInMonths; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}
