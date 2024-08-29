package com.bigfoot.tenantmonitor.model;

import com.bigfoot.tenantmonitor.enums.HeatingType;
import com.bigfoot.tenantmonitor.enums.PropertyCondition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Year;
import java.time.YearMonth;
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
    private float numberOfRooms;
    private int storey;
    private int houseNumber;
    private int price;
    private boolean isFurnished;
    @ManyToOne
    @JoinColumn(name = "property_type_id", nullable = false)
    private PropertyType propertyType;
    @ManyToOne
    @JoinColumn(name = "property_sub_type_id", nullable = false)
    private PropertySubType propertySubType;
    @Enumerated(EnumType.STRING)
    private PropertyCondition propertyCondition;
    private Year yearOfConstruction;
    private YearMonth minRentalPeriod;
    @Enumerated(EnumType.STRING)
    private HeatingType heatingType;
    private int ceilingHeight;
    private boolean lift;                    // mindegy/van (elevator)
    private boolean balcony;                 // mindegy/van (balcony)
    private boolean insulation;              // mindegy/van (insulation)
    private boolean energyEfficient;         // mindegy/igen (energy efficient)
    private boolean accessibilitySupport;    // mindegy/igen (accessible)
    private boolean airConditioning;         // mindegy/van (air conditioning)
    private boolean gardenAccess;            // mindegy/igen (garden access)
    private boolean panelProgram;            // mindegy/részt vett (panel program participation)
    private boolean equipped;                // mindegy/igen (equipped)
    private boolean petsAllowed;             // mindegy/igen (pets allowed)
    private boolean smokingAllowed;          // mindegy/megengedett (smoking allowed)
    @OneToMany(mappedBy = "object_id", fetch = FetchType.EAGER)
    private List<FileMapping> files;
}
