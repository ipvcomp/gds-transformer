package com.ipurvey.gdstransformerservice.gds.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import com.ipurvey.gdstransformerservice.gds.dtos.FlightInfo;
import com.ipurvey.gdstransformerservice.gds.dtos.Journey;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Outbound {

    private FlightInfo flightInfo;
    private Journey journey;
}
