package com.example.maverickbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maverickbank.model.AccountHolder;
import com.example.maverickbank.repo.AccountHolderRepository;

@Service
public class AccountHolderService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;
    
    public String createAccount(AccountHolder accountHolder) {
        accountHolderRepository.save(accountHolder);
        return "Account Created Successfully";
    }
    
    public AccountHolder searchAccount(int accountNo) {
        return accountHolderRepository.findById(accountNo).get();
    }
    
    public String updateAccount(AccountHolder accountHolder) {
        accountHolderRepository.save(accountHolder);
        return "Account Updated Successfully";
    }
    
    public String closeAccount(int accountNo) {
        AccountHolder accountHolder = searchAccount(accountNo);
        accountHolderRepository.delete(accountHolder);
        return "Account Closed Successfully";
    }
    
    public String login(int accountNo, String password) {
        AccountHolder accountHolder = searchAccount(accountNo);
        if(accountHolder != null && accountHolder.getPassword().equals(password)) {
            return "Login Successful";
        }
        return "Invalid Credentials";
    }
    
    public AccountHolder searchByMobileNo(String mobileNo) {
        return accountHolderRepository.findByMobileNo(mobileNo);
    }
    
    public List<AccountHolder> getAllAccounts() {
        return accountHolderRepository.findAll();
    }
}