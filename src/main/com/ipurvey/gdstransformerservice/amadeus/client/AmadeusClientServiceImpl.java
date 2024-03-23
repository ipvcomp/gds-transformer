package com.ipurvey.gdstransformerservice.amadeus.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipurvey.gdstransformerservice.amadeus.client.AmadeusClientService;
import com.ipurvey.gdstransformerservice.amadeus.collections.Booking;
import com.ipurvey.gdstransformerservice.amadeus.collections.PnrDataDto;
import com.ipurvey.gdstransformerservice.amadeus.collections.PnrDto;
import com.ipurvey.gdstransformerservice.amadeus.configuration.AmadeusConfigurations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import com.ipurvey.gdstransformerservice.clients.Client;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class AmadeusClientServiceImpl implements AmadeusClientService {


    private final Client client;
    private final AmadeusConfigurations amadeusConfigurations;
    private final String AMADEUS_BASE_URL;

    @Autowired
    public AmadeusClientServiceImpl(@Qualifier(value = "httpClient") Client client, AmadeusConfigurations amadeusConfigurations) {
        this.client = client;
        this.amadeusConfigurations = amadeusConfigurations;
        this.AMADEUS_BASE_URL = amadeusConfigurations.getBaseUrl();
    }

    @Override
    public List<Booking> getBookings() {
        String bookingUrl  = AMADEUS_BASE_URL.concat("/booking");
        ResponseEntity<List> bookingsResponse = client.call(bookingUrl, HttpMethod.GET, ResponseEntity.EMPTY, List.class);
        List<Booking> bookings = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).convertValue(bookingsResponse.getBody(), new TypeReference<List<Booking>>() {
        });
        int windowSize = 1;
        int limit = bookings.size() - windowSize;
        Random random = new Random();
        int startingPoint = random.nextInt(limit + 1);
        return  bookings.subList(startingPoint,startingPoint+windowSize);
    }

    @Override
    public PnrDto getPnrData(String pnr) throws JsonProcessingException {
        String bookingUrl  = AMADEUS_BASE_URL.concat("/booking/");
        UriComponents build = UriComponentsBuilder.fromUriString(bookingUrl).path(pnr).build();
        String string = build.toString();

        ResponseEntity<?> pnrResponseEntity = client.call(string, HttpMethod.GET, RequestEntity.EMPTY, PnrDataDto.class);
        if(pnrResponseEntity.getStatusCode().is2xxSuccessful())   log.info("PNR data found for PNR: {}", pnr);
        PnrDataDto pnrData = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).convertValue(pnrResponseEntity.getBody(), new TypeReference<PnrDataDto>() {
                });
        return pnrData.getPnrData().get(0);
    }
}
