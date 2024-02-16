package com.ipurvey.gdstransformerservice.amadeus.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class PnrDto {

    private BookingInfoDto bookingInfo;
    private ContactInfoDto contactInfo;
    private List<PassengerInfoDto> passengerInfo;
    private List<FlightInfoDto> flightInfo;
}
