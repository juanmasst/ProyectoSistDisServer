package com.sistDistribuidos.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "auth0")
public class Auth0Properties {
    private String audience;
    private String issuer;
    private String jwkSetUri;
} 