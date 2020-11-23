package com.caglartelef.authenticationservice.model;

import com.caglartelef.authenticationservice.enums.UserRoles;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Authentication {
    private String username;
    private String firstName;
    private String lastName;
    private String accessToken;
    private UserRoles userRoles;
}
