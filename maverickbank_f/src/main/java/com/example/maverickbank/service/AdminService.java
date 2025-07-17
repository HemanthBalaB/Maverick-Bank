package com.example.maverickbank.service;

import com.example.maverickbank.dto.AdminDTO;
import com.example.maverickbank.model.Admin;

public interface AdminService {
    Admin createAdmin(AdminDTO dto);
    Admin getAdminById(Long adminId);
    Admin getAdminByEmail(String email);
    Admin getAdminByMobileNo(String mobileNo);
}
