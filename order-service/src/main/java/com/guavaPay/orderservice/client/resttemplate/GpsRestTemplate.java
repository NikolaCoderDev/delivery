package com.guavaPay.orderservice.client.resttemplate;

import com.guavaPay.orderservice.dto.gpsDto.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GpsRestTemplate {

    @Value("${api_key}")
    private String API_KEY;

    public Response getByAddress(String address) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("maps/api/geocode/json")
                .queryParam("key", API_KEY)
                .queryParam("address", address)
                .build();

        return new RestTemplate().getForEntity(uri.toString(), Response.class).getBody();
    }

}
