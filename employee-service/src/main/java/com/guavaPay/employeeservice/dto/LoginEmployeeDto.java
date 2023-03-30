package com.guavaPay.employeeservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginEmployeeDto {

    private String login;
    private String password;
}