package com.guavaPay.employeeservice.client.feignClient;

import com.guavaPay.employeeservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-service-to-user", url = "http://localhost:8003")
public interface UserFeignClient {

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('courier') || hasAuthority('admin')")
    UserDto getUserById(@PathVariable("id") String id);
}