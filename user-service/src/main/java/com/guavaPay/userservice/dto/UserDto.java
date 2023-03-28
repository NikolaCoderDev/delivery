package com.guavaPay.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String id;
    private String login;
    private String password;
    private String role;
    private String accessToken;
    private String message;
}
