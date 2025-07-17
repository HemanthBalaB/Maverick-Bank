package com.example.maverickbank.repo;

import com.example.maverickbank.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository
    extends JpaRepository<AccountHolder, Long> {

    // findById is inherited; you already have:
    AccountHolder findByMobileNo(String mobileNo);
}
