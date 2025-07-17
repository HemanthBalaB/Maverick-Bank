package com.example.maverickbank.dto;

public class AccountHolderDTO {
    private String name;
    private String email;
    private String mobileNo;
    private String password;
    private double balance;
    private String accountType;
    private String address;
    private Long accountNo; 

    // NEW fields
    private Long branchId;   // link to Branch
    private String ifscCode;
    private String gender;
    private String dob;      // format "yyyy-MM-dd"
    private String aadhar;
    private String pan;

    // Getters / Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Long getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }


    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getAadhar() { return aadhar; }
    public void setAadhar(String aadhar) { this.aadhar = aadhar; }

    public String getPan() { return pan; }
    public void setPan(String pan) { this.pan = pan; }
}
