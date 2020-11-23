package com.caglartelef.authenticationservice.service;

import com.caglartelef.authenticationservice.model.Authentication;
import com.caglartelef.authenticationservice.request.LoginRequest;

public interface IAuthenticationService {
    Authentication getAccessToken(LoginRequest loginRequest);

    void invalidateAccessToken(String accessToken);
}
