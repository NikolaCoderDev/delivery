package com.guavaPay.employeeservice.service;

import com.guavaPay.employeeservice.client.feignClient.EmployeeFeignClient;
import com.guavaPay.employeeservice.config.jwt.JwtUtil;
import com.guavaPay.employeeservice.config.security.EmployeeDetailsServiceImpl;
import com.guavaPay.employeeservice.dto.LoginEmployeeDto;
import com.guavaPay.employeeservice.dto.LoginEmployeeDtoRes;
import com.guavaPay.employeeservice.dto.RegEmployeeDto;
import com.guavaPay.employeeservice.dto.mapper.Mapper;
import com.guavaPay.employeeservice.model.Employee;
import com.guavaPay.employeeservice.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthEmployeeService {

    private final EmployeeDetailsServiceImpl employeeDetailsService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;
    private final EmployeeFeignClient employeeFeignClient;
    private final Mapper mapper;
    private final JwtUtil jwtUtil;

    @PreAuthorize("hasAuthority('admin')")
    public String register(RegEmployeeDto regEmployeeDto) {
        Employee res = employeeRepository.getByLogin(regEmployeeDto.getLogin());
        if (res == null) {
            String ip = employeeFeignClient.getIpAddress().getIp();
            regEmployeeDto.setPassword(BCrypt.hashpw(regEmployeeDto.getPassword(), BCrypt.gensalt()));
            Employee employee = mapper.mapToEmployee(regEmployeeDto);
            employee.setIp_address(ip);
            employeeRepository.save(employee);
            return "Created";
        }
            return "Login exists";
    }

    public LoginEmployeeDtoRes login(LoginEmployeeDto loginEmployeeDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginEmployeeDto.getLogin(), loginEmployeeDto.getPassword()));

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = employeeDetailsService.loadUserByUsername(loginEmployeeDto.getLogin());
            Employee employeeId = employeeRepository.getByLogin(loginEmployeeDto.getLogin());

            Employee employee = new Employee();
            employee.setLogin(loginEmployeeDto.getLogin());
            employee.setPassword(loginEmployeeDto.getPassword());

            String token = jwtUtil.generate(userDetails, employee, employeeId, "ACCESS");
            employee.setAccessToken(token);
            employeeRepository.updateAccessTokenByLogin(employee.getAccessToken(), employee.getLogin());
            employee.setAccessToken(token);
            employee.setRole(employeeId.getRole());
            return mapper.mapToAuthEmployeeDto(employee);
        }
        return null;
    }
}