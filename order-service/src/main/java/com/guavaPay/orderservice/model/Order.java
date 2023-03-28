package com.guavaPay.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue
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
