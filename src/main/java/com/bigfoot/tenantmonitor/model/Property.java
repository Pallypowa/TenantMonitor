package com.bigfoot.tenantmonitor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID ownerId;
    private UUID tenantId;
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
