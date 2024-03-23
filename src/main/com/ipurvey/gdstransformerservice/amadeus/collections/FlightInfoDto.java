package com.ipurvey.gdstransformerservice.amadeus.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class FlightInfoDto {
    private int legId;
    private String carrierCode;
    @Indexed
    private int flightNumber;
    private String departureFrom;
    private String departureAirPort;
    private String departureDateLocal;
    private String departureTimeLocal;
    private String arrivalTo;
    private String arrivalAirPort;
    private String arrivalDateLocal;
    private String arrivalTimeLocal;
}
