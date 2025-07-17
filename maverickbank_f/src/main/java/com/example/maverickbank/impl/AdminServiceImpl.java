package com.example.maverickbank.impl;

import com.example.maverickbank.dto.AdminDTO;
import com.example.maverickbank.model.Admin;
import com.example.maverickbank.repo.AdminRepository;
import com.example.maverickbank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin createAdmin(AdminDTO dto) {
        Admin admin = new Admin();
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setMobileNo(dto.getMobileNo());
        admin.setPassword(passwordEncoder.encode(dto.getPassword())); // BCrypt!
        admin.setAddress(dto.getAddress());
        admin.setRole("ADMIN");
        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdminById(Long adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }

    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Admin getAdminByMobileNo(String mobileNo) {
        return adminRepository.findByMobileNo(mobileNo).orElse(null);
    }
}
