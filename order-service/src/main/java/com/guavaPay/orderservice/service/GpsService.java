package com.guavaPay.orderservice.service;

import com.guavaPay.orderservice.client.resttemplate.GpsRestTemplate;
import com.guavaPay.orderservice.dto.gpsDto.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class GpsService {

    private final GpsRestTemplate gpsRestTemplate;

    public Response getByAddress(String address) {
        return gpsRestTemplate.getByAddress(address);
    }
}