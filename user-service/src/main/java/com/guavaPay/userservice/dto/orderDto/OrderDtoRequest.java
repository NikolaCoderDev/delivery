package com.guavaPay.userservice.dto.orderDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDtoRequest {

    private String title;
    private String description;
    private String deliveryAddress;

}
