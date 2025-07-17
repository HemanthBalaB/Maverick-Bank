package com.example.maverickbank.repo;

import com.example.maverickbank.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findByAccountNo(Long accountNo);

    List<TransactionHistory> findByAccountNoAndTimestampBetween(
        Long accountNo, LocalDateTime start, LocalDateTime end
    );

    List<TransactionHistory> findByAccountNoAndType(
        Long accountNo, String type
    );

    List<TransactionHistory> findByAccountNoAndTypeAndTimestampBetween(
        Long accountNo, String type, LocalDateTime start, LocalDateTime end
    );

    // Mini statement (last 10 txns)
    List<TransactionHistory> findTop10ByAccountNoOrderByTimestampDesc(Long accountNo);
}
