package com.guavaPay.employeeservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginEmployeeDto {

    private String login;
    private String password;
}