package com.bigfoot.tenantmonitor.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApiSpec() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.setComponents(
                new Components().addSecuritySchemes("jwt",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
        );
        openAPI.addServersItem(new Server().url("/"));
        return openAPI;
    }
}