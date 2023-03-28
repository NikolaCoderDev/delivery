package com.guavaPay.orderservice.dto.orderDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String id;
    private String userId;
    private String title;
    private String description;
    private String status;
    private String deliveryAddress;
    private String deliveryLatitude;
    private String deliveryLongitude;
}
