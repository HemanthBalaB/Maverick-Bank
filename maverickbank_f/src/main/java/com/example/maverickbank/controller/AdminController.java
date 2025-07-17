package com.example.maverickbank.controller;

import com.example.maverickbank.dto.AdminDTO;
import com.example.maverickbank.model.Admin;
import com.example.maverickbank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Admin registration (for setup, then protect/remove this in production)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AdminDTO dto) {
        Admin admin = adminService.createAdmin(dto);
        return ResponseEntity.ok("Admin registered with ID: " + admin.getAdminId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getAdminById(id);
        return (admin != null)
            ? ResponseEntity.ok(admin)
            : ResponseEntity.notFound().build();
    }

    // Add more endpoints as needed (update, delete, etc.)
}
