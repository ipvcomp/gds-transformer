package com.ipurvey.gdstransformerservice.gds.Impl;

import com.ipurvey.gdstransformerservice.amadeus.collections.PnrData;
import com.ipurvey.gdstransformerservice.gds.dtos.FlightBookingRequest;
import com.ipurvey.gdstransformerservice.gds.service.GdsTransformerService;
import org.springframework.stereotype.Service;

import java.util.List;


public class AmadeusGdsTransformerServiceImpl implements GdsTransformerService {

    private final List<PnrData> pnrData;

    public AmadeusGdsTransformerServiceImpl(List<PnrData> pnrData) {
        this.pnrData = pnrData;
    }

    @Override
    public  List<FlightBookingRequest> transform() {
        //TODO write logic to transform
        return null;
    }
}
