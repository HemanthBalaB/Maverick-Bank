package com.example.maverickbank.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class AccountHolder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNo;

    private String name;
    private String email;
    private String mobileNo;
    private String password;
    private Double balance;
    private String accountType;
    private String address;
    private String ifscCode;

    private String gender;

    private LocalDate dob;

    private String aadhar;
    private String pan;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    // Getters & Setters for all fields below

    public Long getAccountNo() { return accountNo; }
    public void setAccountNo(Long accountNo) { this.accountNo = accountNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getAadhar() { return aadhar; }
    public void setAadhar(String aadhar) { this.aadhar = aadhar; }

    public String getPan() { return pan; }
    public void setPan(String pan) { this.pan = pan; }

    public Branch getBranch() { return branch; }
    public void setBranch(Branch branch) { this.branch = branch; }
}
