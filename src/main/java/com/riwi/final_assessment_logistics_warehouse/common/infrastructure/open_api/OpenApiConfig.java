package com.riwi.final_assessment_logistics_warehouse.common.infrastructure.open_api;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
    @Value("${server.port}")
    private String serverPort;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        // Create a dynamic server URL based on the server port
        String localServerUrl = String.format("http://localhost:%s%s", this.serverPort, this.contextPath);

        // Define the server for OpenAPI
        Server localServer = new Server().url(localServerUrl).description("Local Development Server");

        Info info = new Info().title("Logistics Warehouse API").version("1.0.0")
                .description("REST API to manage palets loads in a warehouse.");

        // Define security scheme of the API
        Components components = new Components().addSecuritySchemes("bearerAuth", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT").name("Authorization"));

        return new OpenAPI().servers(Collections.singletonList(localServer)).info(info).components(components);
    }
}