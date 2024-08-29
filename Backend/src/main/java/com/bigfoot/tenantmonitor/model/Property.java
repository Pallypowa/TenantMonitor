package com.bigfoot.tenantmonitor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User owner;
    @OneToMany(mappedBy = "property")
    private List<TransactionHistory> transactionHistories;
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "tenantId")
    private User tenant;
    private String name;
    private int zipcode;
    private String street;
    private int size;
    private int storey;
    private int houseNumber;
    private int price;
    private boolean isFurnished;
    private String propertyType;
    @OneToMany(mappedBy = "object_id", fetch = FetchType.EAGER)
    private List<FileMapping> files;
}
