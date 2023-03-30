package com.guavaPay.employeeservice.model;

import com.guavaPay.employeeservice.dto.IpDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String number;
    private String ip_address;

    @PrePersist
    public void prePersist() {
        if(status == null)
            status = "free";
        if(createdOn == null)
            createdOn = LocalDateTime.now();
    }
}
