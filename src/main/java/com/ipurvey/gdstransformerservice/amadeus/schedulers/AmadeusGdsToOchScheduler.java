package com.ipurvey.gdstransformerservice.amadeus.schedulers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ipurvey.gdstransformerservice.Utils.PnrMapper;
import com.ipurvey.gdstransformerservice.amadeus.collections.Booking;
import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
import com.ipurvey.gdstransformerservice.amadeus.collections.PnrDto;
import com.ipurvey.gdstransformerservice.amadeus.collections.ProcessingStatus;
import com.ipurvey.gdstransformerservice.gds.dtos.FlightBookingRequest;
import com.ipurvey.gdstransformerservice.gds.factory.Transformer;
import com.ipurvey.gdstransformerservice.gds.factory.TransformerFactory;
import com.ipurvey.gdstransformerservice.gds.factory.TransformerType;
import com.ipurvey.gdstransformerservice.gds.service.PnrDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AmadeusGdsToOchScheduler {

    private final TransformerFactory transformerFactory;
    private final Transformer<Pnr> gdsTransformer;
    private final PnrDataService pnrDataService;

    @Autowired
    public AmadeusGdsToOchScheduler(TransformerFactory transformerFactory, PnrDataService pnrDataService) {
        this.transformerFactory = transformerFactory;
        gdsTransformer = transformerFactory.createTransformer(TransformerType.GDS);
        this.pnrDataService = pnrDataService;
    }


    @Scheduled(cron = "${amadeus.to.och.scheduler.cron.expression}")
    @Transactional(rollbackFor = {RuntimeException.class})
    public void amadeusGdsToOchFlightBookingRequest() throws JsonProcessingException {
        try{
        log.info("Looking in database for PNR records with STATUS {} ", ProcessingStatus.PENDING);
        List<Pnr> pnrs = pnrDataService.findByStatus(ProcessingStatus.PENDING);
        log.info("number of pnrs to be processed {}", pnrs.size());
        List<FlightBookingRequest> transformedFlightBookingRequests = new ArrayList<>(pnrs.size());
        for (Pnr pnr : pnrs) {
            pnrDataService.updateStatus(pnr.getId(),pnr, ProcessingStatus.PROCESSING);
            FlightBookingRequest flightBookingRequest = gdsTransformer.transform(pnr);
            log.info("transformed request for OCH {} ", flightBookingRequest.getFlightInfo());
            transformedFlightBookingRequests.add(flightBookingRequest);
            pnrDataService.updateStatus(pnr.getId(),pnr, ProcessingStatus.PROCESSED);
        }
    }catch (RuntimeException exception){
            exception.printStackTrace();
        log.info("exception while processing records");

    }
    }

    private void update(Booking booking) throws JsonProcessingException {
        PnrDto pnrByPnrNumber = pnrDataService.getPnrByPnrNumber(booking.getPnr());
        if (pnrByPnrNumber != null)
            pnrDataService.updatePnrData(booking.getBookingRef(), PnrMapper.map(pnrByPnrNumber, new Pnr()));
    }

}
