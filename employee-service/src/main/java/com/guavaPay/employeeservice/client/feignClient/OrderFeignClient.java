package com.guavaPay.employeeservice.client.feignClient;

import com.guavaPay.employeeservice.dto.orderDto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "employee-service", url = "http://localhost:8004/order")
public interface OrderFeignClient {

    @GetMapping("/myTasks")
    @PreAuthorize("hasAuthority('courier')")
    List<OrderDto> getOrdersByCourierId();

    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    List<OrderDto> getAll();

    @PutMapping("/accept/{orderId}")
    @PreAuthorize("hasAuthority('courier') || hasAuthority('admin')")
    ResponseEntity<String> acceptOrderByCourierId(@PathVariable("orderId") String orderId);

    @PutMapping("/assign/{courierId}/{orderId}")
    @PreAuthorize("hasAuthority('admin')")
    ResponseEntity<String> assignCourierToOrder(@PathVariable("courierId") String courierId, @PathVariable("orderId") String orderId);
}