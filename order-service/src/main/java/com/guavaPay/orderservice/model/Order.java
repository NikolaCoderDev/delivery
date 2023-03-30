package com.guavaPay.orderservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String employeeId;
    private String title;
    private String description;
    private String status;
    private String deliveryLatitude;
    private String deliveryLongitude;
    private String deliveryAddress;
    private LocalDateTime createdOn;

    @PrePersist
    public void prePersist() {
        if(createdOn == null)
            createdOn = LocalDateTime.now();
    }
}
