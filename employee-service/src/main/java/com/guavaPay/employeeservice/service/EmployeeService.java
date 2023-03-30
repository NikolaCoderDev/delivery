package com.guavaPay.employeeservice.service;

import com.guavaPay.employeeservice.client.feignClient.EmployeeFeignClient;
import com.guavaPay.employeeservice.config.jwt.JwtUtil;
import com.guavaPay.employeeservice.dto.EmployeeDto;
import com.guavaPay.employeeservice.dto.IpDto;
import com.guavaPay.employeeservice.dto.gpsDto.Location;
import com.guavaPay.employeeservice.dto.gpsDto.Response;
import com.guavaPay.employeeservice.dto.mapper.Mapper;
import com.guavaPay.employeeservice.model.Employee;
import com.guavaPay.employeeservice.repository.EmployeeRepository;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DatabaseReader databaseReader;
    private final EmployeeFeignClient employeeFeignClient;
    private final GpsService gpsService;
    private final Mapper mapper;
    private final JwtUtil jwtUtil;

    public EmployeeDto getById(String id) {
        Employee employee = null;
        if (id != null) {
            employee = employeeRepository.getById(Long.valueOf(id));
            return mapper.mapToEmployeeDto(employee);
        }
       return null;
    }

    public void updateCourierCoordinates(String latLng, String ipAddress) {
        Response coordinates = gpsService.getByLatLng(latLng);
        Location location = coordinates.getResult()[0].getGeometry().getLocation();
        String lat = String.valueOf(location.getLat());
        String lng = String.valueOf(location.getLng());
        String address = coordinates.getResult()[0].getAddress();
        employeeRepository.updateCourierCoordinates(lat, lng, address, ipAddress);
    }

    @Scheduled(fixedDelay = 1000)
    public void getEmployeeCoordinatesByIp() throws IOException, GeoIp2Exception {
        IpDto ip = employeeFeignClient.getIpAddress();
        InetAddress ipAddress = InetAddress.getByName(ip.getIp());
        com.maxmind.geoip2.record.Location location = databaseReader.city(ipAddress).getLocation();
        String latLng = location.getLatitude() + "," + location.getLongitude();
        updateCourierCoordinates(latLng, ip.getIp());
    }
}