package com.guavaPay.userservice.client.feignClient;

import com.guavaPay.userservice.dto.orderDto.AddressDto;
import com.guavaPay.userservice.dto.orderDto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${spring.application.name}", url = "${order.service.url}")
public interface OrderFeignClient {

    @GetMapping("/myOrders")
    @PreAuthorize("hasAuthority('user')")
    List<OrderDto> getOrdersByUserId();

    @PutMapping("/cancel/{orderId}")
    @PreAuthorize("hasAuthority('user')")
    ResponseEntity<String> cancelOrderByUserId(@PathVariable("orderId") String orderId);

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAuthority('user')")
    ResponseEntity<String> updateAddressByUserId(@RequestBody AddressDto address, @PathVariable("orderId") String orderId);
}