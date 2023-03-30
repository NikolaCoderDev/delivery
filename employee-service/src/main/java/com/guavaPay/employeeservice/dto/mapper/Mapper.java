package com.guavaPay.employeeservice.dto.mapper;

import com.guavaPay.employeeservice.dto.EmployeeDto;
import com.guavaPay.employeeservice.dto.LoginEmployeeDtoRes;
import com.guavaPay.employeeservice.dto.RegEmployeeDto;
import com.guavaPay.employeeservice.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public LoginEmployeeDtoRes mapToAuthEmployeeDto(Employee employee) {
        return LoginEmployeeDtoRes.builder()
                .login(employee.getLogin())
                .accessToken(employee.getAccessToken())
                .role(employee.getRole())
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
                .number(employee.getNumber())
                .build();
    }

    public Employee mapToEmployee(RegEmployeeDto regEmployeeDto) {
        Employee employee = new Employee();
        employee.setLogin(regEmployeeDto.getLogin());
        employee.setRole(regEmployeeDto.getRole());
        employee.setPassword(regEmployeeDto.getPassword());
        employee.setNumber(regEmployeeDto.getNumber());
        return employee;
    }

    public Employee mapToEmployee(LoginEmployeeDtoRes loginEmployeeDtoRes) {
        Employee employee = new Employee();
        employee.setLogin(loginEmployeeDtoRes.getLogin());
        employee.setRole(loginEmployeeDtoRes.getRole());
        return employee;
    }
}