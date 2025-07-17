package com.example.maverickbank.service;

import com.example.maverickbank.model.*;
import com.example.maverickbank.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.contains("@")) {
            Admin admin = adminRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
            return User.builder().username(admin.getEmail()).password(admin.getPassword()).roles("ADMIN").build();
        }

        try {
            Long accNo = Long.parseLong(username);
            AccountHolder user = accountHolderRepository.findById(accNo)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return User.builder().username(String.valueOf(user.getAccountNo())).password(user.getPassword()).roles("USER").build();
        } catch (NumberFormatException ignore) {}

        Employee emp = employeeRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found"));
        return User.builder().username(emp.getEmail()).password(emp.getPassword()).roles("EMPLOYEE").build();
    }
}
