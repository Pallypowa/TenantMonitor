package com.bigfoot.tenantmonitor.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccessTokenDTO {
    private String accessToken;
    private String tokenType;
    private int expiresIn;
}
