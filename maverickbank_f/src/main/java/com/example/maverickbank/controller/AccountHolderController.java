package com.example.maverickbank.controller;

import com.example.maverickbank.dto.AccountHolderDTO;
import com.example.maverickbank.model.AccountHolder;
import com.example.maverickbank.service.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AccountHolderController {

    @Autowired
    private AccountHolderService accountHolderService;

    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody AccountHolderDTO dto) {
        AccountHolder saved = accountHolderService.createAccount(dto);
        Map<String,Object> resp = new HashMap<>();
        resp.put("message",   "Account created successfully!");
        resp.put("accountNo", saved.getAccountNo());
        resp.put("ifscCode",  saved.getIfscCode());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/searchAccount/{accountNo}")
    public AccountHolder searchAccount(@PathVariable Long accountNo) {
        return accountHolderService.searchAccount(accountNo);
    }

    @PutMapping("/updateAccount")
    public ResponseEntity<?> updateAccount(@RequestBody AccountHolderDTO dto) {
        AccountHolder updated = accountHolderService.updateAccount(dto);
        return ResponseEntity.ok(Map.of(
            "message", "Account updated successfully",
            "accountNo", updated.getAccountNo()
        ));
    }
    @GetMapping("/getBalance/{accountNo}")
    public ResponseEntity<Double> getBalance(@PathVariable Long accountNo) {
        AccountHolder ah = accountHolderService.searchAccount(accountNo);
        return ResponseEntity.ok(ah.getBalance());
    }


    @DeleteMapping("/closeAccount/{accountNo}")
    public ResponseEntity<String> closeAccount(@PathVariable Long accountNo) {
        boolean success = accountHolderService.closeAccount(accountNo);
        if (success) {
            return ResponseEntity.ok("Account Closed Successfully");
        }
        return ResponseEntity.status(404).body("Account Not Found");
    }
    
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> creds) {
        Long accountNo = Long.parseLong(creds.get("accountNo"));
        String pw       = creds.get("password");
        AccountHolder ah = accountHolderService.login(accountNo, pw);
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "accountHolder", ah
        ));
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
