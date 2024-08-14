package com.bigfoot.tenantmonitor.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//Have to add these so that the objectMapper can parse the value.
// For some reason it expects a "with" prefix for the methods.
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = TokenDTO.TokenDTOBuilder.class)
public class TokenDTO {
    private String accessToken;
    private int accessTokenExpire;
    private String refreshToken;
    private int refreshTokenExpire;
}
