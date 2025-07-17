package com.example.maverickbank;

import com.example.maverickbank.service.AccountHolderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class AccountHolderServiceTest {
    @InjectMocks
    private AccountHolderService accountHolderService;

    public AccountHolderServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sampleTest() {
        assertTrue(true);
    }
} 