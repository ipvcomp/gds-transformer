package com.ipurvey.gdstransformerservice.gds.factory;

import com.ipurvey.gdstransformerservice.gds.dtos.FlightBookingRequest;

import java.util.List;

public interface Transformer<T> {
    List<FlightBookingRequest> transform(T data);
}
