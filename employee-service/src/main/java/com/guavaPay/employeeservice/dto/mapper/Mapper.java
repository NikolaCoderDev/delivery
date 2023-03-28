package com.guavaPay.employeeservice.dto.mapper;

import com.guavaPay.employeeservice.dto.EmployeeDto;
import com.guavaPay.employeeservice.dto.LoginEmployeeDtoRes;
import com.guavaPay.employeeservice.dto.RegEmployeeDto;
import com.guavaPay.employeeservice.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Employee mapToEmployee(LoginEmployeeDtoRes dto) {

        Long id = null;

        if (dto.getId() != null) {
            id = Long.valueOf(dto.getId());
        }

        return Employee.builder()
                .id(id)
                .role(dto.getRole())
                .login(dto.getLogin())
                .accessToken(dto.getAccessToken())
                .build();
    }

    public LoginEmployeeDtoRes mapToAuthEmployeeDto(Employee user) {
        return LoginEmployeeDtoRes.builder()
                .login(user.getLogin())
                .accessToken(user.getAccessToken())
                .role(user.getRole())
                .build();
    }

    public EmployeeDto mapToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId().toString())
                .login(employee.getLogin())
                .role(employee.getRole())
                .status(employee.getStatus())
                .longitude(employee.getLatitude())
                .latitude(employee.getLatitude())
                .address(employee.getAddress())
                .build();
    }

    public Employee mapToEmployee(RegEmployeeDto regEmployeeDto) {
        return Employee.builder()
                .login(regEmployeeDto.getLogin())
                .role(regEmployeeDto.getRole())
                .password(regEmployeeDto.getPassword())
                .build();
    }
}
