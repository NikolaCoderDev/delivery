package com.guavaPay.employeeservice.client.feignClient;

import com.guavaPay.employeeservice.dto.IpDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "get-ip-address", url = "https://api64.ipify.org?format=json")
public interface EmployeeFeignClient {

    @GetMapping
    IpDto getIpAddress();
}