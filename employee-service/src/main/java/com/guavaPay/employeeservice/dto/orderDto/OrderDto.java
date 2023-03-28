package com.guavaPay.employeeservice.dto.orderDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    private String id;
    private String userId;
    private String employeeId;
    private String title;
    private String description;
    private String status;
    private String deliveryAddress;
    private String deliveryLatitude;
    private String deliveryLongitude;
}
