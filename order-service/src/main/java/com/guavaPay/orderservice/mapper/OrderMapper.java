package com.guavaPay.orderservice.mapper;

import com.guavaPay.orderservice.dto.orderDto.OrderDto;
import com.guavaPay.orderservice.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDto mapToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId().toString())
                .title(order.getTitle())
                .status(order.getStatus())
                .description(order.getDescription())
                .deliveryLongitude(order.getDeliveryLongitude())
                .deliveryLatitude(order.getDeliveryLatitude())
                .deliveryAddress(order.getDeliveryAddress())
                .userId(order.getUserId())
                .build();
    }

    public Order mapTo(OrderDto dto) {
        Order order = new Order();
        order.setTitle(dto.getTitle());
        order.setStatus(dto.getStatus());
        order.setDescription(dto.getDescription());
        order.setDeliveryLatitude(dto.getDeliveryLatitude());
        order.setDeliveryLongitude(dto.getDeliveryLongitude());
        order.setUserId(dto.getUserId());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        return order;
    }
}