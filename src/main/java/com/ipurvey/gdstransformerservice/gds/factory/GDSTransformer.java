package com.ipurvey.gdstransformerservice.gds.factory;

import com.ipurvey.gdstransformerservice.amadeus.collections.PnrData;
import com.ipurvey.gdstransformerservice.gds.dtos.FlightBookingRequest;

import java.util.Collections;
import java.util.List;
public class GDSTransformer implements Transformer<List<PnrData>> {
    @Override
    public List<FlightBookingRequest> transform(List<PnrData> object) {
        return Collections.emptyList();
    }
}
