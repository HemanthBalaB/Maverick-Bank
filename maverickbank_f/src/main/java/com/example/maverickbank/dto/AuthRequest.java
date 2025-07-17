package com.example.maverickbank.dto;

public class AuthRequest {
    private String username;   // For admin/employee (email or ID)
    private Long accountNo;    // For user login (account number)
    private String password;

    // No-args constructor (important for Spring)
    public AuthRequest() {}

    // All-args constructor (optional, for convenience)
    public AuthRequest(String username, Long accountNo, String password) {
        this.username = username;
        this.accountNo = accountNo;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Long getAccountNo() { return accountNo; }
    public void setAccountNo(Long accountNo) { this.accountNo = accountNo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
