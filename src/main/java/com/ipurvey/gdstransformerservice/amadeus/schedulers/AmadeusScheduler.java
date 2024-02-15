package com.ipurvey.gdstransformerservice.amadeus.schedulers;


import com.ipurvey.gdstransformerservice.amadeus.collections.PnrData;
import com.ipurvey.gdstransformerservice.gds.dtos.FlightBookingRequest;
import com.ipurvey.gdstransformerservice.gds.factory.Transformer;
import com.ipurvey.gdstransformerservice.gds.factory.TransformerFactory;
import com.ipurvey.gdstransformerservice.gds.factory.TransformerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class AmadeusScheduler {


    private final Transformer<List<PnrData>> gdsTransformer = TransformerFactory.createTransformer(TransformerType.GDS);
    @Scheduled(cron = "${amadeus.scheduler.cron.expression}")
    public void runScheduler() {
        //TODO : make to bookings
        List<FlightBookingRequest> transformed = gdsTransformer.transform(Collections.emptyList());
        log.info("Scheduler executed at the start of every minute");
    }

}
