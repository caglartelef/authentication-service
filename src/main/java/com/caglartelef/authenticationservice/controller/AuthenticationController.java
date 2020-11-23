package com.caglartelef.authenticationservice.controller;

import com.caglartelef.authenticationservice.model.Authentication;
import com.caglartelef.authenticationservice.request.LoginRequest;
import com.caglartelef.authenticationservice.response.InternalApiResponse;
import com.caglartelef.authenticationservice.response.LoginResponse;
import com.caglartelef.authenticationservice.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final IAuthenticationService authService;

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/login")
    public InternalApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        log.debug("Authentication Controller login username: {}", loginRequest.getUsername());
        Authentication authentication = authService.getAccessToken(loginRequest);
        LoginResponse loginResponse = LoginResponse.builder()
                .username(authentication.getUsername())
                .accessToken(authentication.getAccessToken())
                .firstName(authentication.getFirstName())
                .lastName(authentication.getLastName())
                .userRoles(authentication.getUserRoles())
                .build();
        return InternalApiResponse.<LoginResponse>builder()
                .payload(loginResponse)
                .hasError(false)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logout")
    public InternalApiResponse<String> logout(@RequestHeader("Authorization") String token) {
        log.debug("Authentication Controller logout token: {}", token);
        authService.invalidateAccessToken(token);
        return InternalApiResponse.<String>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload("success")
                .build();
    }

}
