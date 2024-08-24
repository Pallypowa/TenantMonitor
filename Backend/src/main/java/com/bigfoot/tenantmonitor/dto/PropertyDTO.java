package com.bigfoot.tenantmonitor.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PropertyDTO {
    @NotBlank
    private UUID id;
    @NotBlank
    private UUID ownerId;
    private UUID tenantId;
    @Size(min = 2, max = 100)
    private String name;
    @NotBlank
    private int zipcode;
    @Size(max = 100)
    private String street;
    @Min(1)
    private int size;
    private int storey;
    private int houseNumber;
    @NotBlank
    @Min(1)
    private int price;
    private boolean isFree;
    private boolean isFurnished;
    private String propertyType;
    private TenantDTO tenant;

    @Override
    public String toString() {
        return "PropertyDTO{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", tenantId=" + tenantId +
                ", name='" + name + '\'' +
                ", zipcode=" + zipcode +
                ", street='" + street + '\'' +
                ", size=" + size +
                ", storey=" + storey +
                ", houseNumber=" + houseNumber +
                ", price=" + price +
                ", isFree=" + isFree +
                ", isFurnished=" + isFurnished +
                ", propertyType='" + propertyType + '\'' +
                ", tenant=" + tenant +
                '}';
    }
}
