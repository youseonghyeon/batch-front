package com.example.batchfront.repository;

import com.example.batchfront.Entity.TransferHistory;
import com.example.batchfront.Entity.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {

    long countByStatus(TransferStatus transferStatus);
}
