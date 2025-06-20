package com.example.maverickbank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.maverickbank.model.AccountHolder;
import com.example.maverickbank.service.AccountHolderService;

@RestController
public class AccountHolderController {

    @Autowired
    private AccountHolderService accountHolderService;
    
    @PostMapping("/createAccount")
    public String createAccount(@RequestBody AccountHolder accountHolder) {
        return accountHolderService.createAccount(accountHolder);
    }
    
    @GetMapping("/searchAccount/{accountNo}")
    public AccountHolder searchAccount(@PathVariable int accountNo) {
        return accountHolderService.searchAccount(accountNo);
    }
    
    @PutMapping("/updateAccount")
    public String updateAccount(@RequestBody AccountHolder accountHolder) {
        return accountHolderService.updateAccount(accountHolder);
    }
    
    @DeleteMapping("/closeAccount/{accountNo}")
    public String closeAccount(@PathVariable int accountNo) {
        return accountHolderService.closeAccount(accountNo);
    }
    
    @PostMapping("/login")
    public String login(@RequestParam int accountNo, @RequestParam String password) {
        return accountHolderService.login(accountNo, password);
    }
    
    @GetMapping("/searchByMobileNo/{mobileNo}")
    public AccountHolder searchByMobileNo(@PathVariable String mobileNo) {
        return accountHolderService.searchByMobileNo(mobileNo);
    }
    
    @GetMapping("/getAllAccounts")
    public List<AccountHolder> getAllAccounts() {
        return accountHolderService.getAllAccounts();
    }
}