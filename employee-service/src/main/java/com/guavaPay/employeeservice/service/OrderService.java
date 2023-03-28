package com.guavaPay.employeeservice.service;

import com.guavaPay.employeeservice.client.feignClient.OrderFeignClient;
import com.guavaPay.employeeservice.client.feignClient.UserFeignClient;
import com.guavaPay.employeeservice.config.jwt.JwtUtil;
import com.guavaPay.employeeservice.dto.EmployeeDto;
import com.guavaPay.employeeservice.dto.LoginEmployeeDtoRes;
import com.guavaPay.employeeservice.dto.orderDto.UserOrdersDto;
import com.guavaPay.employeeservice.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private final EmployeeRepository employeeRepository;
    private final OrderFeignClient orderFeignClient;
    private final UserFeignClient userFeignClient;
    private final EmployeeService employeeService;

    private final JwtUtil jwtUtil;

    public List<UserOrdersDto> getOrdersByCourierId() {
         return orderFeignClient.getOrdersByCourierId().stream().map(o -> UserOrdersDto.builder()
                 .id(o.getId())
                 .userDto(userFeignClient.getUserById(o.getUserId()))
                 .employee(employeeService.getById(o.getEmployeeId()))
                 .status(o.getStatus())
                 .title(o.getTitle())
                 .deliveryLatitude(o.getDeliveryLatitude())
                 .deliveryLongitude(o.getDeliveryLongitude())
                 .deliveryAddress(o.getDeliveryAddress())
                 .description(o.getDescription())
                 .build())
                 .toList();
    }

    @PreAuthorize("hasAuthority('admin')")
    public List<UserOrdersDto> getAllOrders() {
        return orderFeignClient.getAll().stream().map(o -> UserOrdersDto.builder()
                .id(o.getId())
                .userDto(userFeignClient.getUserById(o.getUserId()))
                .employee(employeeService.getById(o.getEmployeeId()))
                .status(o.getStatus())
                .title(o.getTitle())
                .deliveryLatitude(o.getDeliveryLatitude())
                .deliveryLongitude(o.getDeliveryLongitude())
                .deliveryAddress(o.getDeliveryAddress())
                .description(o.getDescription())
                .build()).toList();
    }

    @PreAuthorize("hasAuthority('admin')")
    public List<UserOrdersDto> getAllOrderById(String id) {
        return orderFeignClient.getAll().stream().filter(order -> order.getId().equals(id)).map(o -> UserOrdersDto.builder()
                .id(o.getId())
                .userDto(userFeignClient.getUserById(o.getUserId()))
                .employee(employeeService.getById(o.getEmployeeId()))
                .status(o.getStatus())
                .title(o.getTitle())
                .deliveryLatitude(o.getDeliveryLatitude())
                .deliveryLongitude(o.getDeliveryLongitude())
                .deliveryAddress(o.getDeliveryAddress())
                .description(o.getDescription())
                .build()).toList();
    }

    @PreAuthorize("hasAuthority('admin')")
    public List<EmployeeDto> getAllCourierStatuses() {
        return employeeRepository.getAll()
                .stream()
                .map(employee -> EmployeeDto.builder()
                .login(employee.getLogin())
                .role(employee.getRole())
                .address(employee.getAddress())
                .longitude(employee.getLongitude())
                .latitude(employee.getLatitude())
                .status(employee.getStatus())
                .id(employee.getId().toString())
                .build())
                .toList();
    }

    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> assignCourier(String id, String orderId) {
        employeeRepository.assignСourier("assigned", new Long(id));
        return orderFeignClient.assignCourierToOrder(id, orderId);
    }

    public void onDelivery(String authorization) {
        String token = jwtUtil.removeBearer(authorization);
        LoginEmployeeDtoRes dto = jwtUtil.getJwtParsetUserDto(token);
        employeeRepository.onDelivery("on delivery", new Long(dto.getId()));
    }
}