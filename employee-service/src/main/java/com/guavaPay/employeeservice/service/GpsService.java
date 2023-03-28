package com.guavaPay.employeeservice.service;

import com.guavaPay.employeeservice.client.resttemplate.GpsRestTemplate;
import com.guavaPay.employeeservice.dto.gpsDto.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GpsService {

    private final GpsRestTemplate gpsRestTemplate;

    public Response getByLatLng(String latLng) {
        return gpsRestTemplate.getByLatLng(latLng);
    }
}