package com.caglartelef.authenticationservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfiguration {
    private String realm;
    private String clientId;
    private String username;
    private String password;
    private String clientSecret;
    private String authServerUrl;
}

