package com.guavaPay.userservice.model;

import jakarta.persistence.*;
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
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String role;
    @Size(max = 5000)
    private String accessToken;
    private String password;
    private LocalDateTime createdOn;

    @PrePersist
    public void prePersist() {
        if(createdOn == null)
            createdOn = LocalDateTime.now();
        if (role == null)
            role = "user";
    }
}
