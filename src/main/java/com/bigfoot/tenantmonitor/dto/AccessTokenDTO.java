package com.bigfoot.tenantmonitor.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = AccessTokenDTO.AccessTokenDTOBuilder.class)
public class AccessTokenDTO {
    @NotBlank
    private String accessToken;
    @NotBlank
    private int accessTokenExpire;
}
