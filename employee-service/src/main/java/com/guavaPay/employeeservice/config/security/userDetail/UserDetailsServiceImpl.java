package com.guavaPay.employeeservice.config.security.userDetail;

import com.guavaPay.employeeservice.config.jwt.JwtUtil;
import com.guavaPay.employeeservice.config.security.EmployeeDetailsImpl;
import com.guavaPay.employeeservice.dto.EmployeeDto;
import com.guavaPay.employeeservice.dto.LoginEmployeeDtoRes;
import com.guavaPay.employeeservice.dto.mapper.Mapper;
import com.guavaPay.employeeservice.model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JwtUtil jwtUtil;
    private final Mapper mapper;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        LoginEmployeeDtoRes employeeDto = jwtUtil.getJwtParsetUserDto(token);
        Employee employee = mapper.mapToEmployee(employeeDto);
        UserDetails userDetails = new EmployeeDetailsImpl(employee);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(employeeDto.getLogin(), token);
        authentication.setDetails(userDetails);
        return (UserDetails) authentication.getDetails();
    }
}