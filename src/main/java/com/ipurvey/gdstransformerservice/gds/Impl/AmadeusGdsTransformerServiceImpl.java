package com.ipurvey.gdstransformerservice.gds.Impl;

import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
import com.ipurvey.gdstransformerservice.gds.dtos.FlightBookingRequest;
import com.ipurvey.gdstransformerservice.gds.service.GdsTransformerService;

import java.util.List;


public class AmadeusGdsTransformerServiceImpl implements GdsTransformerService {

    private final List<Pnr> pnrData;

    public AmadeusGdsTransformerServiceImpl(List<Pnr> pnrData) {
        this.pnrData = pnrData;
    }

    @Override
    public  List<FlightBookingRequest> transform() {
        //TODO write logic to transform
        return null;
    }
}
