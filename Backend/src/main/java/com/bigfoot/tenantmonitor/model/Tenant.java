package com.bigfoot.tenantmonitor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate leaseStartDate;
    private LocalDate leaseEndDate;

    @OneToMany(mappedBy = "tenant")
    private List<TransactionHistory> transactionHistories;
    @OneToOne(mappedBy = "tenant")
    private Property property;
}
