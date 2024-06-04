package com.bigfoot.tenantmonitor.repository;

import com.bigfoot.tenantmonitor.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransHistoryRepository extends JpaRepository<TransactionHistory, UUID> {
}
