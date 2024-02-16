package com.ipurvey.gdstransformerservice.gds.factory;

import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
import com.ipurvey.gdstransformerservice.gds.dtos.FlightBookingRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class GDSTransformer implements Transformer<List<Pnr>> {
    @Override
    public List<FlightBookingRequest> transform(List<Pnr> object) {
        return Collections.emptyList();
    }
}
