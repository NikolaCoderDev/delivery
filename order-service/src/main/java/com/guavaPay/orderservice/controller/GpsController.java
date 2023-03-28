package com.guavaPay.orderservice.controller;

import com.guavaPay.orderservice.dto.gpsDto.Response;
import com.guavaPay.orderservice.model.Order;
import com.guavaPay.orderservice.service.GpsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class GpsController {

    private final GpsService gpsService;

    @GetMapping("/location_by_coordinates")
    public Response getByLatLng(@RequestParam String latlng) {
        return gpsService.getByLatLng(latlng);
    }

    @GetMapping("/location_by_address")
    public Response getByAddress(@RequestParam String address) {
        return gpsService.getByAddress(address);
    }
}