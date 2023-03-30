package com.guavaPay.employeeservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegEmployeeDto {

    private String login;
    private String role;
    private String password;
    private String number;
}
