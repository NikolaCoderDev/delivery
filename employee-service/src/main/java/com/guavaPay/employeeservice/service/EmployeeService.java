package com.guavaPay.employeeservice.service;

import com.guavaPay.employeeservice.config.jwt.JwtUtil;
import com.guavaPay.employeeservice.dto.EmployeeDto;
import com.guavaPay.employeeservice.dto.LoginEmployeeDtoRes;
import com.guavaPay.employeeservice.dto.gpsDto.Location;
import com.guavaPay.employeeservice.dto.gpsDto.Response;
import com.guavaPay.employeeservice.dto.mapper.Mapper;
import com.guavaPay.employeeservice.model.Employee;
import com.guavaPay.employeeservice.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final GpsService gpsService;
    private final Mapper mapper;
    private final JwtUtil jwtUtil;

    @PreAuthorize("hasAuthority('admin') || hasAuthority('courier')")
    public EmployeeDto getById(String id) {
        Employee employee = employeeRepository.getById(Long.valueOf(id));
        return mapper.mapToEmployeeDto(employee);
    }

    public void updateCourierCoordinates(String latLng, String authorization) {
        String token = jwtUtil.removeBearer(authorization);
        LoginEmployeeDtoRes dto = jwtUtil.getJwtParsetUserDto(token);
        Response coordinates = gpsService.getByLatLng(latLng);
        Location location = coordinates.getResult()[0].getGeometry().getLocation();
        String lat = String.valueOf(location.getLat());
        String lng = String.valueOf(location.getLng());
        long id = Long.parseLong(dto.getId());
        String address = coordinates.getResult()[0].getAddress();
        employeeRepository.updateCourierCoordinates(lat, lng, address, id);
    }
}
