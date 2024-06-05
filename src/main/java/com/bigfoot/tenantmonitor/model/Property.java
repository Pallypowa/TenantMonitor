package com.bigfoot.tenantmonitor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "ownerId")
    private Owner owner;
    @OneToMany(mappedBy = "property")
    private List<TransactionHistory> transactionHistories;
    @OneToOne
    @JoinColumn(name = "tenantId")
    private Tenant tenant;
    private String name;
    private int zipcode;
    private String street;
    private int size;
    private int storey;
    private int houseNumber;
    private int price;
    private boolean isFree;
    private boolean isFurnished;
    private String propertyType;

}
