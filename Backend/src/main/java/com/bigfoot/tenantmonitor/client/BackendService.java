package com.bigfoot.tenantmonitor.client;

import com.bigfoot.tenantmonitor.client.jwt.JwtStore;
import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.bigfoot.tenantmonitor.dto.TokenDTO;
import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import com.bigfoot.tenantmonitor.model.Property;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
public class BackendService {
    private final RestClient restClient;

    public BackendService(@Qualifier("backendClient") RestClient restClient) {
        this.restClient = restClient;

    }

    public boolean register(RegistrationDTO registrationDTO){
        try{
            return restClient
                    .post()
                    .uri("/api/v1/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(registrationDTO)
                    .retrieve()
                    .toBodilessEntity()
                    .getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            System.out.printf("Registration failed, client error %s", e.getStatusCode());
        } catch (HttpServerErrorException e){
            System.out.printf("Registration failed, server error %s", e.getStatusCode());
        }

        return false;
    }

    public boolean authenticate(LoginDTO loginDTO){
        try {
            ResponseEntity<TokenDTO> jwtResponse = restClient
                    .post()
                    .uri("/api/v1/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(loginDTO)
                    .retrieve()
                    .toEntity(TokenDTO.class);
            TokenDTO tokenDTO = jwtResponse.getBody();
            JwtStore.setAccessToken(tokenDTO.getAccessToken());
            JwtStore.setRefreshToken(tokenDTO.getRefreshToken());
            return true;
        } catch (HttpClientErrorException e){
            System.out.printf("Login failed... invalid username or password %s", e.getStatusCode());
        } catch (HttpServerErrorException e){
            System.out.printf("Login failed... server error %s", e.getStatusCode());
        }
        return false;
    }

    public List<PropertyDTO> getProperties(){
        try {
            ResponseEntity<List<PropertyDTO>> properties = restClient
                    .get()
                    .uri("/api/v1/property")
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<List<PropertyDTO>>() {
                    });
            return properties.getBody();
        } catch (HttpClientErrorException e){
            System.out.printf("Token expired %s", e.getStatusCode());
        } catch (HttpServerErrorException e){
            System.out.printf("Fetch failed... server error %s", e.getStatusCode());
        }
        return List.of();
    }

}
