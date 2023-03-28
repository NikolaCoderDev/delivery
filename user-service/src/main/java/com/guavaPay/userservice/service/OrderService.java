package com.guavaPay.userservice.service;

import com.guavaPay.userservice.config.jwt.JwtUtil;
import com.guavaPay.userservice.config.rabbitMq.OrderMqConfig;
import com.guavaPay.userservice.dto.UserDto;
import com.guavaPay.userservice.dto.orderDto.OrderDto;
import com.guavaPay.userservice.dto.orderDto.OrderDtoRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private final JwtUtil jwtUtil;
    private final RabbitTemplate template;

    @PreAuthorize("hasAuthority('user')")
    public void save(String authorization, OrderDtoRequest dtoReq) {
        OrderDto dto = new OrderDto();
        String token = jwtUtil.removeBearer(authorization);
        UserDto userDto = jwtUtil.getJwtParsetUserDto(token);
        dto.setUserId(userDto.getId());
        dto.setDescription(dtoReq.getDescription());
        dto.setTitle(dtoReq.getTitle());
        dto.setDeliveryAddress(dtoReq.getDeliveryAddress());
        template.convertAndSend(OrderMqConfig.EXCHANGE, OrderMqConfig.ROUTING_KEY, dto);
    }
}