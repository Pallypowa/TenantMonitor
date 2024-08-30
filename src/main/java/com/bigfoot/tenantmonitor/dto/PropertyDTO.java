package com.bigfoot.tenantmonitor.dto;

import com.bigfoot.tenantmonitor.model.FileMapping;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;
import java.util.List;
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
    private float numberOfRooms;
    private int storey;
    private int houseNumber;
    @NotBlank
    @Min(1)
    private int price;
    private boolean isFree;
    private boolean isFurnished;
    private String propertyType;
    private String propertySubType;
    private String propertyCondition;
    private int yearOfConstruction;
    private YearMonth minRentalPeriod;
    private String heatingType;
    private int ceilingHeight;
    private boolean lift;
    private boolean balcony;
    private boolean insulation;
    private boolean energyEfficient;
    private boolean accessibilitySupport;
    private boolean airConditioning;
    private boolean gardenAccess;
    private boolean panelProgram;
    private boolean equipped;
    private boolean petsAllowed;
    private boolean smokingAllowed;
    private List<FileMapping> files;
    //private List<TransactionHistoryDTO> transactionHistories;
    private TenantDTO tenant;
}
