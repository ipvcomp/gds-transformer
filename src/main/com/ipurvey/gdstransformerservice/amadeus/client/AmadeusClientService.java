package com.ipurvey.gdstransformerservice.amadeus.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ipurvey.gdstransformerservice.amadeus.collections.Booking;
import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
import com.ipurvey.gdstransformerservice.amadeus.collections.PnrDataDto;
import com.ipurvey.gdstransformerservice.amadeus.collections.PnrDto;

import java.util.List;

public interface AmadeusClientService {


    List<Booking> getBookings();
  PnrDto getPnrData(String pnr) throws JsonProcessingException;


}
