package com.guavaPay.employeeservice.controller;

import com.guavaPay.employeeservice.dto.EmployeeDto;
import com.guavaPay.employeeservice.dto.orderDto.UserOrdersDto;
import com.guavaPay.employeeservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("admin")
@Tag(name = "Admin controller", description = "")
public class AdminController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "View orders. [admin permission]", description = "View all orders for the delivery of parcels that are assigned to me.")
    public List<UserOrdersDto> getAll() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @Operation(summary = "View order coordinates by id. [admin permission]", description = "Put token here.")
    public List<UserOrdersDto> getById(@PathVariable String id) {
        return orderService.getAllOrderById(id);
    }
    @GetMapping("/couriers")
    @Operation(summary = "View courier statuses. [admin permission]", description = "Put token here.")
    public List<EmployeeDto> getAllCouriersStatuses() {
        return orderService.getAllCourierStatuses();
    }

    @PutMapping("/assign/{courierId}/{orderId}")
    @Operation(summary = "Assign a courier to order. [admin permission]", description = "Put token here.")
    public ResponseEntity<String> assignCourierToOrder(@PathVariable("courierId") String courierId, @PathVariable("orderId") String orderId) {
        return orderService.assignCourier(courierId, orderId);
    }
}