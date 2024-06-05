package com.bigfoot.tenantmonitor.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int amount;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "property")
    private Property property;
    @ManyToOne
    @JoinColumn(name = "tenant")
    private Tenant tenant;
}
