package com.guavaPay.employeeservice.controller;

import com.guavaPay.employeeservice.dto.LoginEmployeeDto;
import com.guavaPay.employeeservice.dto.LoginEmployeeDtoRes;
import com.guavaPay.employeeservice.dto.RegEmployeeDto;
import com.guavaPay.employeeservice.service.AuthEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Authorization controller", description = "You may generate token here.")
public class AuthEmployeeController {

    private final AuthEmployeeService employeeService;

    @Operation(summary = "Only an administrator can create an employee account.", description = "Put token here.")
    @RequestMapping(path="/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody RegEmployeeDto regEmployeeDto) {
         String res = employeeService.register(regEmployeeDto);
         if (!res.equals("Login exists")) {
             return new ResponseEntity<>(res, HttpStatus.OK);
         }
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @Operation(summary = "Login without token.", description = "You should login and get token here.")
    @RequestMapping(path="/login", method = RequestMethod.POST)
    public ResponseEntity<LoginEmployeeDtoRes> login(@RequestBody LoginEmployeeDto employee) {
        LoginEmployeeDtoRes res = employeeService.login(employee);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Logout with token.", description = "You should logout with token here.")
    @RequestMapping(path="/logout", method = RequestMethod.POST)
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }
}