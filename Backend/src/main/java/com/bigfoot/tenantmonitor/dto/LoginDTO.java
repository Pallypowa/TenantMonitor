package com.bigfoot.tenantmonitor.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {
    @Size(min = 2, max = 100)
    private String userName;
    @Size(min = 6)
    private String password;
}
