package com.ipurvey.gdstransformerservice.gds.dtos;

import com.ipurvey.services.och.response.Passenger;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PassengerDetails {
    private int numberOfPassengersInParty;

    @Size(max = 9, message = "Maximum number of 9 passenger per booking exceeded. Please resubmit")
    private List<Passenger> passengerNames;

}
