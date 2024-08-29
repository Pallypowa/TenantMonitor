package com.bigfoot.tenantmonitor.client;

import com.bigfoot.tenantmonitor.client.jwt.JwtStore;
import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import com.bigfoot.tenantmonitor.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.io.InputStream;
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

    public void uploadFile(InputStream fileData, String fileName, long contentLength, String mimeType, String propertyId) {
        try{
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            InputStreamResource inputStreamResource = new InputStreamResource(fileData) {
                @Override
                public String getFilename() {
                    return fileName;
                }

                @Override
                public long contentLength() {
                    return contentLength;
                }
            };
            body.add("file", inputStreamResource);
            restClient
                .post()
                .uri(String.format("/api/v1/file?objectId=%s", propertyId))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .toBodilessEntity();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    public ResponseEntity<Resource> getFile(String fileId){
        return restClient
                .get()
                .uri(String.format("/api/v1/file/%s", fileId))
                .retrieve()
                .toEntity(Resource.class);

    }

    public RestClient.ResponseSpec deleteFile(String fileId){
        return restClient
                .delete()
                .uri(String.format("/api/v1/file/%s", fileId))
                .retrieve();
    }

    public PropertyDTO getProperty(String propertyId){
        try {
            ResponseEntity<PropertyDTO> property = restClient
                    .get()
                    .uri("/api/v1/property/" + propertyId)
                    .retrieve()
                    .toEntity(PropertyDTO.class);
            return property.getBody();
        } catch (HttpClientErrorException e){
            System.out.printf("Token expired %s", e.getStatusCode());
        } catch (HttpServerErrorException e){
            System.out.printf("Fetch failed... server error %s", e.getStatusCode());
        }
        return null;
    }
}
