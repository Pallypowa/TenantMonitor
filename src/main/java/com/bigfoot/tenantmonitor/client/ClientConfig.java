package com.bigfoot.tenantmonitor.client;

import com.bigfoot.tenantmonitor.client.jwt.JwtInterceptor;
import com.bigfoot.tenantmonitor.client.jwt.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.login.LoginOverlay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;



@Configuration
public class ClientConfig {
    @Value("${application.backend.url}")
    private String BACKEND_URL;

    @Bean(name = "backendClient")
    public RestClient genericBackendServiceClient(JwtTokenService jwtTokenService) {
        return RestClient
                .builder()
                .baseUrl(BACKEND_URL)
                .requestInterceptor(new JwtInterceptor(jwtTokenService))
                .build();
    }

    //Need a different client because I want to inject it into the JwtInterceptor for token refresh usage
    @Bean(name = "jwtClient")
    public RestClient jwtRefreshRestClient(){
        return RestClient
                .builder()
                .baseUrl(BACKEND_URL)
                .build();
    }
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean(name = "loginOverlay")
    public LoginOverlay loginOverlay() {
        return new LoginOverlay();
    }

    @Bean(name = "signUpOverlay")
    public LoginOverlay signUpOverlay() {
        return new LoginOverlay();
    }
}
