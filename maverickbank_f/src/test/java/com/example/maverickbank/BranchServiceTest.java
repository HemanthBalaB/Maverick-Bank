package com.example.maverickbank;

import com.example.maverickbank.service.BranchService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class BranchServiceTest {
    @InjectMocks
    private BranchService branchService;

    public BranchServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sampleTest() {
        assertTrue(true);
    }
} 