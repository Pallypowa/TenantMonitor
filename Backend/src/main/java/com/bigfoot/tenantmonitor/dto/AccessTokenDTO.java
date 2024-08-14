package com.bigfoot.tenantmonitor.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = AccessTokenDTO.AccessTokenDTOBuilder.class)
public class AccessTokenDTO {
    private String accessToken;
    private int accessTokenExpire;
}
