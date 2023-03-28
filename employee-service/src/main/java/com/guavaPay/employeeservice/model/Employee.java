package com.guavaPay.employeeservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employee")
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String role;
    @Size(max = 5000)
    private String accessToken;
    private String password;
    private String status;
    private String latitude;
    private String longitude;
    private String address;
    private LocalDateTime createdOn;

    @PrePersist
    public void prePersist() {
        if(status == null)
            status = "free";
        if(createdOn == null)
            createdOn = LocalDateTime.now();
    }
}
