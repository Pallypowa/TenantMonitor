package com.bigfoot.tenantmonitor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TenantDTO {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
}
