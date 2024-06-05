package com.bigfoot.tenantmonitor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PropertyDTO {
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
    private TenantDTO tenant;
}
