package com.bigfoot.tenantmonitor.web.dto.tenant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TenantDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
