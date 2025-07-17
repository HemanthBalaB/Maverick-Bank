package com.example.maverickbank;

import com.example.maverickbank.service.BeneficiaryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class BeneficiaryServiceTest {
    @InjectMocks
    private BeneficiaryService beneficiaryService;

    public BeneficiaryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sampleTest() {
        assertTrue(true);
    }
} 