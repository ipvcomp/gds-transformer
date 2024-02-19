package com.ipurvey.gdstransformerservice.amadeus.schedulers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ipurvey.gdstransformerservice.Utils.PnrMapper;
import com.ipurvey.gdstransformerservice.amadeus.collections.Booking;
import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
import com.ipurvey.gdstransformerservice.amadeus.collections.PnrDto;
import com.ipurvey.gdstransformerservice.amadeus.collections.ProcessingStatus;
import com.ipurvey.gdstransformerservice.configuration.AmqpAdminConfig;
import com.ipurvey.gdstransformerservice.gds.dtos.FlightBookingRequest;
import com.ipurvey.gdstransformerservice.gds.factory.Transformer;
import com.ipurvey.gdstransformerservice.gds.factory.TransformerFactory;
import com.ipurvey.gdstransformerservice.gds.factory.TransformerType;
import com.ipurvey.gdstransformerservice.gds.service.PnrDataService;
import com.ipurvey.gdstransformerservice.producer.RabbitMQProducer;
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
    private final RabbitMQProducer producer;

    @Autowired
    public AmadeusGdsToOchScheduler(TransformerFactory transformerFactory, PnrDataService pnrDataService, RabbitMQProducer producer) {
        this.transformerFactory = transformerFactory;
        gdsTransformer = transformerFactory.createTransformer(TransformerType.GDS);
        this.pnrDataService = pnrDataService;
        this.producer = producer;
    }


    @Scheduled(cron = "${amadeus.to.och.scheduler.cron.expression}")
    @Transactional(rollbackFor = {Exception.class})
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
        producer.sendMessage(AmqpAdminConfig.GDS_AMADEUS_TO_OCH_OUTGOING_QUEUE,transformedFlightBookingRequests);
    }catch (Exception exception){
        log.error("exception while processing records",exception);
            throw  new RuntimeException(exception);

    }
    }

    private void update(Booking booking) throws JsonProcessingException {
        PnrDto pnrByPnrNumber = pnrDataService.getPnrByPnrNumber(booking.getPnr());
        if (pnrByPnrNumber != null)
            pnrDataService.updatePnrData(booking.getBookingRef(), PnrMapper.map(pnrByPnrNumber, new Pnr()));
    }

}
