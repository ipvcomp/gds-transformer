package com.ipurvey.gdstransformerservice.gds.dtos;

import lombok.*;



@Data
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Journey {


    private BookingDepartureDetails departureDetails;


    private BookingArrivalDetails arrivalDetails;


    private String carrierCode;
    private String carrierName;


    private String flightNumber;


    private String legId; //1 inbound , 2 outbound

    private String evtStatus;

}
