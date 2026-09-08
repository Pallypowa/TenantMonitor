package com.bigfoot.tenantmonitor.web.dto.property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String country;
    private String state;
    private int zipcode;
    private String city;
    private String streetName;
    private int houseNumber;


}
