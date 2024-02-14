package com.ipurvey.gdstransformerservice.gds.service;

import com.ipurvey.gdstransformerservice.gds.dtos.FlightBookingRequest;

import java.util.List;

public interface GdsTransformerService {

    public List<FlightBookingRequest>  transform();
}
