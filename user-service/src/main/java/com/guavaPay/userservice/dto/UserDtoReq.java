package com.guavaPay.userservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDtoReq {

    private String login;
    private String password;
}