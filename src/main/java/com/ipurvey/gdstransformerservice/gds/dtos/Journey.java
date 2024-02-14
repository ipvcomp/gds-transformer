package com.ipurvey.gdstransformerservice.gds.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Journey {

    @NotNull(message = "Departure Details can not be empty")
    private BookingDepartureDetails departureDetails;

    @NotNull(message = "Arrival Details can not be empty")
    private BookingArrivalDetails arrivalDetails;

    @NotBlank(message = "Carried Code can not be blank")
    private String carrierCode;
    private String carrierName;

    @NotBlank(message = "flightNumber id must not be empty!")
    private String flightNumber;

    @NotBlank(message = "legId must not be empty!")
    private String legId; //1 inbound , 2 outbound

    private String evtStatus;

}
