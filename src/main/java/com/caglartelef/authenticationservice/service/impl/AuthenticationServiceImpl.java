package com.caglartelef.authenticationservice.service.impl;

import com.caglartelef.authenticationservice.configuration.KeycloakConfiguration;
import com.caglartelef.authenticationservice.enums.UserRoles;
import com.caglartelef.authenticationservice.exception.exceptions.CredentialException;
import com.caglartelef.authenticationservice.model.Authentication;
import com.caglartelef.authenticationservice.request.LoginRequest;
import com.caglartelef.authenticationservice.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.TokenVerifier;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final KeycloakConfiguration keycloakConfiguration;

    @Override
    public Authentication getAccessToken(LoginRequest loginRequest) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakConfiguration.getAuthServerUrl())
                .realm(keycloakConfiguration.getRealm())
                .username(loginRequest.getUsername())
                .password(loginRequest.getPassword())
                .clientId(keycloakConfiguration.getClientId())
                .clientSecret(keycloakConfiguration.getClientSecret())
                .build();
        try {
            String accessTokenString = keycloak.tokenManager().getAccessTokenString();
            AccessToken accessToken = TokenVerifier.create(accessTokenString, AccessToken.class).getToken();
            UserRoles userRole = UserRoles.getEnumFromValue(accessToken.getRealmAccess().getRoles());
            Authentication authentication = Authentication.builder()
                    .username(accessToken.getPreferredUsername())
                    .accessToken(accessTokenString)
                    .firstName(accessToken.getGivenName())
                    .lastName(accessToken.getFamilyName())
                    .userRoles(userRole)
                    .build();
            return authentication;
        } catch (NotAuthorizedException | VerificationException e) {
            throw new CredentialException("Invalid user credentials");
        } catch (BadRequestException e) {
            throw new CredentialException("User not active");
        }
    }

    @Override
    public void invalidateAccessToken(String accessToken) {
        try {
            String[] tokenArray = accessToken.split(" ");
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(keycloakConfiguration.getAuthServerUrl())
                    .realm(keycloakConfiguration.getRealm())
                    .username(keycloakConfiguration.getUsername())
                    .password(keycloakConfiguration.getPassword())
                    .clientId(keycloakConfiguration.getClientId())
                    .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                    .build();
            TokenManager tokenManager = keycloak.tokenManager();
            tokenManager.invalidate(tokenArray[1]);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
