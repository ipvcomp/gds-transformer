package com.ipurvey.gdstransformerservice.amadeus.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "pnrs")
public class Pnr extends BaseEntity {

    @Id
    private String id;
    private BookingInfoDto bookingInfo;
    private ContactInfoDto contactInfo;
    private List<PassengerInfoDto> passengerInfo;
    private List<FlightInfoDto> flightInfo;
    private ProcessingStatus processingStatus=ProcessingStatus.PENDING;
}
