package com.bigfoot.tenantmonitor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JwtDTO {
    private String accessToken;
    private String tokenType;
    private int expiresIn;
}
