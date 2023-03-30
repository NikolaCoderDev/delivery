package com.guavaPay.userservice.controller;

import com.guavaPay.userservice.dto.UserDto;
import com.guavaPay.userservice.dto.UserDtoReq;
import com.guavaPay.userservice.model.User;
import com.guavaPay.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "User controller", description = "You may generate token here.")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register without token.", description = "You may register here without token.")
    @RequestMapping(path="/register", method = RequestMethod.POST)
    public User register(@RequestBody UserDtoReq user) {
        return userService.register(user);
    }

    @Operation(summary = "Login without token.", description = "You should login and get token here.")
    @RequestMapping(path="/login", method = RequestMethod.POST)
    public UserDto login(@RequestBody UserDtoReq user) {
        return userService.login(user);
    }

    @Operation(summary = "Logout with token.", description = "You should logout with token here.")
    @RequestMapping(path="/logout", method = RequestMethod.POST)
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id. [admin, courier permission]")
    public UserDto getById(@PathVariable("id") String id) {
        return userService.getById(id);
    }
}