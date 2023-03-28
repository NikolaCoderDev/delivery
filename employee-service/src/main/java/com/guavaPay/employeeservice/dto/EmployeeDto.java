package com.guavaPay.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private String id;
    private String login;
    private String role;
    private String status;
    private String latitude;
    private String longitude;
    private String address;
}
