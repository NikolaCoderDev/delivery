package com.guavaPay.userservice.config.security.employeeDetail;

import com.guavaPay.userservice.config.jwt.JwtUtil;
import com.guavaPay.userservice.config.security.UserDetailsImpl;
import com.guavaPay.userservice.dto.UserDto;
import com.guavaPay.userservice.dto.mapper.Mapper;
import com.guavaPay.userservice.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmployeeDetailsServiceImpl implements UserDetailsService {

    private final JwtUtil jwtUtil;
    private final Mapper mapper;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        UserDto employeeDto = jwtUtil.getJwtParsetUserDto(token);
        User employee = mapper.mapToUser(employeeDto);
        UserDetails userDetails = new UserDetailsImpl(employee);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(employeeDto.getLogin(), token);
        authentication.setDetails(userDetails);
        return (UserDetails) authentication.getDetails();
    }
}