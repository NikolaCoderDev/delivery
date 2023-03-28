package com.guavaPay.orderservice.service;

import com.guavaPay.orderservice.config.jwt.JwtUtil;
import com.guavaPay.orderservice.dto.UserDto;
import com.guavaPay.orderservice.dto.gpsDto.Location;
import com.guavaPay.orderservice.dto.gpsDto.Response;
import com.guavaPay.orderservice.dto.orderDto.AddressDto;
import com.guavaPay.orderservice.dto.orderDto.OrderDto;
import com.guavaPay.orderservice.mapper.OrderMapper;
import com.guavaPay.orderservice.model.Order;
import com.guavaPay.orderservice.repository.OrderRepository;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Data
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final GpsService gpsService;
    private final JwtUtil jwtUtil;

    public Order save(OrderDto dto) {
        if (!dto.getDeliveryAddress().equals("") && dto.getDeliveryAddress() != null) {
            OrderDto coordinates = getCoordinates(dto.getDeliveryAddress());
            dto.setDeliveryLatitude(coordinates.getDeliveryLatitude());
            dto.setDeliveryLongitude(coordinates.getDeliveryLongitude());
            dto.setDeliveryAddress(coordinates.getDeliveryAddress());
            Order order = orderMapper.mapTo(dto);
            order.setStatus("booked");
            return orderRepository.save(order);
        }
        return null;
    }

    @PreAuthorize("hasAuthority('user')")
    public void updateAddressByUserId(AddressDto address, String authorization, long orderId) {
        String token = jwtUtil.removeBearer(authorization);
        UserDto user = jwtUtil.getJwtParsetUserDto(token);
        OrderDto coordinates = getCoordinates(address.getName());
        orderRepository.updateAddressByUserId(coordinates.getDeliveryAddress(), coordinates.getDeliveryLatitude(), coordinates.getDeliveryLongitude(), user.getId(), orderId);
    }

    @PreAuthorize("hasAuthority('user')")
    public List<Order> getOrdersByUserId(String authorization) {
        String token = jwtUtil.removeBearer(authorization);
        UserDto dto = jwtUtil.getJwtParsetUserDto(token);
        return orderRepository.getOrdersByUserId(dto.getId());
    }

    @PreAuthorize("hasAuthority('courier') || hasAuthority('admin')")
    public List<Order> getOrdersByCourierId(String authorization) {
        String token = jwtUtil.removeBearer(authorization);
        UserDto dto = jwtUtil.getJwtParsetUserDto(token);
        return orderRepository.getOrdersByEmployeeId(dto.getId());
    }

    @PreAuthorize("hasAuthority('admin')")
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @PreAuthorize("hasAuthority('user')")
    public void cancelOrderByUserId(String authorization, String orderId) {
        String token = jwtUtil.removeBearer(authorization);
        UserDto dto = jwtUtil.getJwtParsetUserDto(token);
        orderRepository.cancelOrderByUserId("canceled", dto.getId(), new Long(orderId));
    }

    public OrderDto getCoordinates(String address) {
        OrderDto dto = new OrderDto();
        Response coordinates = gpsService.getByAddress(address);
        Location location = coordinates.getResult()[0].getGeometry().getLocation();
        String lat = String.valueOf(location.getLat());
        String lng = String.valueOf(location.getLng());
        dto.setDeliveryLatitude(lat);
        dto.setDeliveryLongitude(lng);
        dto.setDeliveryAddress(coordinates.getResult()[0].getAddress());
        return dto;
    }

    @PreAuthorize("hasAuthority('courier')")
    public void acceptOrderByCourierId(String orderId, String authorization) {
        String token = jwtUtil.removeBearer(authorization);
        UserDto dto = jwtUtil.getJwtParsetUserDto(token);
        orderRepository.acceptOrderByCourierId("accepted", dto.getId(), new Long(orderId));
    }

    @PreAuthorize("hasAuthority('admin')")
    public void assignCourierToOrder(String courierId, String ordererId) {
        orderRepository.assignCourierToOrder("signed", courierId, Long.parseLong(ordererId));
    }
}