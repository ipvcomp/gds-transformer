package com.ipurvey.gdstransformerservice.Utils;

import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
import com.ipurvey.gdstransformerservice.amadeus.collections.PnrDto;
import org.springframework.web.bind.annotation.Mapping;


public class PnrMapper {



        public static Pnr  map(PnrDto pnrDto, Pnr pnr) {
            pnr.setBookingInfo(pnrDto.getBookingInfo());
            pnr.setContactInfo(pnrDto.getContactInfo());
            pnr.setPassengerInfo(pnrDto.getPassengerInfo());
            pnr.setFlightInfo(pnrDto.getFlightInfo());
            return pnr;
        }


}