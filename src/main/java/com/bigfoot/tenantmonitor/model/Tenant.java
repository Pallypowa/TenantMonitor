package com.bigfoot.tenantmonitor.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "tenant")
    private List<TransactionHistory> transactionHistories;
    @OneToOne(mappedBy = "tenant")
    private Property property;
}
