package com.bigfoot.tenantmonitor.client.jwt;

import com.bigfoot.tenantmonitor.dto.AccessTokenDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

//The purpose of this class is to
@Service
public class JwtTokenService {
    private final RestClient restClient;

    public JwtTokenService(@Qualifier("jwtClient")RestClient restClient) {
        this.restClient = restClient;
    }

    public String refreshToken(String refreshToken){
        ResponseEntity<AccessTokenDTO> accessToken = restClient
                .post()
                .uri(String.format("api/v1/refresh/%s", refreshToken))
                .retrieve()
                .toEntity(AccessTokenDTO.class);

        if(accessToken.getStatusCode().is4xxClientError()){
            throw new RuntimeException("Couldn't fetch access token");
        }

        return Objects.requireNonNull(accessToken.getBody()).getAccessToken();

    }
}
