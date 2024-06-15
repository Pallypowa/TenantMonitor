package com.bigfoot.tenantmonitor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TenantDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
