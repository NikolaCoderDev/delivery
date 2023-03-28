package com.guavaPay.employeeservice.dto.orderDto;

import com.guavaPay.employeeservice.dto.EmployeeDto;
import com.guavaPay.employeeservice.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOrdersDto {

    private String id;
    private String title;
    private String description;
    private String status;
    private String deliveryAddress;
    private String deliveryLatitude;
    private String deliveryLongitude;
    private UserDto userDto;
    private EmployeeDto employee;
}
