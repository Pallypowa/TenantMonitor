package com.bigfoot.tenantmonitor.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String accessToken;
    @NotBlank
    private int accessTokenExpire;
    @NotBlank
    private String refreshToken;
    @NotBlank
    private int refreshTokenExpire;
}
