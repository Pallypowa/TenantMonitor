package com.bigfoot.tenantmonitor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
}
