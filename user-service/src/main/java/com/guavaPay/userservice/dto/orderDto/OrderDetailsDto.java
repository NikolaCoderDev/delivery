package com.guavaPay.userservice.dto.orderDto;

import com.guavaPay.userservice.dto.employeeDto.EmployeeDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailsDto {

    private String id;
    private String userId;
    private String title;
    private String description;
    private String deliveryAddress;
    private EmployeeDto employee;
}