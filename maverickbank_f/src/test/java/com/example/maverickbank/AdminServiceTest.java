package com.example.maverickbank;

import com.example.maverickbank.service.AdminService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class AdminServiceTest {
    @InjectMocks
    private AdminService adminService;

    public AdminServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sampleTest() {
        assertTrue(true);
    }
} 