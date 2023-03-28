package com.guavaPay.userservice.dto.orderDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

    private String userId;
    private String title;
    private String description;
    private String deliveryAddress;
}
