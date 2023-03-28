package com.guavaPay.employeeservice.controller;

import com.guavaPay.employeeservice.client.feignClient.OrderFeignClient;
import com.guavaPay.employeeservice.dto.orderDto.UserOrdersDto;
import com.guavaPay.employeeservice.service.EmployeeService;
import com.guavaPay.employeeservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Courier controller", description = "")
public class CourierController {

    private final OrderFeignClient orderFeignClient;
    private final EmployeeService employeeService;
    private final OrderService orderService;

    @GetMapping("/coordinates")
    @Operation(summary = "Update courier coordinates. [courier permission]", description = "Put token here.")
    public void updateCourierCoordinates(@RequestParam String latlng, @RequestHeader String authorization) {
        employeeService.updateCourierCoordinates(latlng, authorization);
    }

    @PutMapping("/accept/{orderId}")
    @Operation(summary = "Accept order. [courier permission]", description = "Put token here.")
    public ResponseEntity<String> acceptOrderByCourierId(@PathVariable("orderId") String orderId, @RequestHeader String authorization) {
        orderService.onDelivery(authorization);
        return orderFeignClient.acceptOrderByCourierId(orderId);
    }

    @GetMapping("/myTasks")
    @Operation(summary = "View my orders. [courier permission]", description = "View all orders for the delivery of parcels that are assigned to me.")
    public List<UserOrdersDto> getOrdersByCourierId() {
        return orderService.getOrdersByCourierId();
    }
}