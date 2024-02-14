package com.ipurvey.gdstransformerservice.gds.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PassengerDetails {
    private int numberOfPassengersInParty;

    private List<Passenger> passengerNames;

}
