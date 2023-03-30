package com.guavaPay.userservice.client.feignClient;

import com.guavaPay.userservice.dto.employeeDto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-service", url = "${employee_feign}")
public interface EmployeeFeignClient {

    @GetMapping("/{id}")
    EmployeeDto getCourierById(@PathVariable String id);
}