package com.guavaPay.orderservice.controller;

import com.guavaPay.orderservice.config.rabbitMq.OrderMqConfig;
import com.guavaPay.orderservice.dto.orderDto.AddressDto;
import com.guavaPay.orderservice.dto.orderDto.OrderDto;
import com.guavaPay.orderservice.model.Order;
import com.guavaPay.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    @RabbitListener(queues = OrderMqConfig.QUEUE)
    public void save(OrderDto dto) {
        orderService.save(dto);
    }

    @GetMapping("/myOrders")
    public List<Order> getOrdersByUserId(@RequestHeader String authorization) {
        return orderService.getOrdersByUserId(authorization);
    }
    @GetMapping("/myTasks")
    public List<Order> getOrdersByCourierId(@RequestHeader String authorization) {
        return orderService.getOrdersByCourierId(authorization);
    }

    @PutMapping("/accept/{orderId}")
    public ResponseEntity<String> acceptOrderByCourierId(@PathVariable("orderId") String orderId, @RequestHeader String authorization) {
         orderService.acceptOrderByCourierId(orderId, authorization);
        return new ResponseEntity<>("Successfully accepted!", HttpStatus.OK);
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrderByUserId(@RequestHeader String authorization, @PathVariable("orderId") String orderId) {
        orderService.cancelOrderByUserId(authorization, orderId);
        return new ResponseEntity<>("Successfully canceled!", HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    void updateAddressByUserId(@RequestBody AddressDto address, @PathVariable("orderId") String orderId, @RequestHeader String authorization) {
        orderService.updateAddressByUserId(address, authorization, Long.parseLong(orderId));
    }

    @PutMapping("/assign/{courierId}/{orderId}")
    ResponseEntity<String> assignCourierToOrder(@PathVariable String courierId, @PathVariable String orderId) {
        orderService.assignCourierToOrder(courierId, orderId);
        return new ResponseEntity<>("Successfully assigned!", HttpStatus.OK);
    }

}