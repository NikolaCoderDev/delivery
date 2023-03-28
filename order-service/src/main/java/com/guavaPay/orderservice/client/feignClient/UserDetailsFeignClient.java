package com.guavaPay.order;

import com.guavaPay.orderservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
@FeignClient(name = "order-service-niyaz", url = "http://localhost:8787/user_details")
public interface UserDetailsFeignClient {

    @GetMapping("/{login}")
    Optional<UserDto> getUserByLogin(@PathVariable("login") String login);
}