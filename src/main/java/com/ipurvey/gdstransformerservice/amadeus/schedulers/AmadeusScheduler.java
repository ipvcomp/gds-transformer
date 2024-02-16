package com.ipurvey.gdstransformerservice.amadeus.schedulers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ipurvey.gdstransformerservice.Utils.PnrMapper;
import com.ipurvey.gdstransformerservice.amadeus.client.AmadeusClientService;
import com.ipurvey.gdstransformerservice.amadeus.collections.Booking;
import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
import com.ipurvey.gdstransformerservice.amadeus.collections.PnrDataDto;
import com.ipurvey.gdstransformerservice.amadeus.collections.PnrDto;
import com.ipurvey.gdstransformerservice.gds.factory.Transformer;
import com.ipurvey.gdstransformerservice.gds.factory.TransformerFactory;
import com.ipurvey.gdstransformerservice.gds.factory.TransformerType;
import com.ipurvey.gdstransformerservice.gds.service.BookingService;
import com.ipurvey.gdstransformerservice.gds.service.PnrDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@Slf4j
public class AmadeusScheduler {

    private final TransformerFactory transformerFactory;
    private final Transformer<List<Pnr>> gdsTransformer;
    private  final BookingService bookingService;
    private final PnrDataService pnrDataService;

    @Autowired
    public AmadeusScheduler(TransformerFactory transformerFactory, AmadeusClientService amadeusClientService, BookingService bookingService, PnrDataService pnrDataService) {
        this.transformerFactory = transformerFactory;
        gdsTransformer  = transformerFactory.createTransformer(TransformerType.GDS);
        this.bookingService = bookingService;
        this.pnrDataService = pnrDataService;
    }


    @Scheduled(cron = "${amadeus.scheduler.cron.expression}")
    public void runScheduler() throws JsonProcessingException {
        log.info("Getting Bookings from AMADEUS");
        List<Booking> bookings = bookingService.getBookings();
        log.info("Number of records received {}", bookings.size());
        for (Booking booking : bookings) {
            Booking byBookingReference = bookingService.findByBookingReference(booking.getBookingRef());
            if(byBookingReference!=null && StringUtils.hasText(byBookingReference.getId()) && !booking.getStatus().equalsIgnoreCase(byBookingReference.getStatus())){
                bookingService.updateBooking(byBookingReference.getBookingRef(), booking);
                PnrDto pnrByPnrNumber = pnrDataService.getPnrByPnrNumber(booking.getPnr());
                if (pnrByPnrNumber!=null)
                 pnrDataService.updatePnrData(booking.getBookingRef(),  PnrMapper.map(pnrByPnrNumber, new Pnr()));
            }else{
                log.info("No booking found its a new booking {}", booking.getBookingRef());
                 PnrDto pnrByPnrNumber = pnrDataService.getPnrByPnrNumber(booking.getPnr());
                if (pnrByPnrNumber!=null)
                     pnrDataService.savePnrData(PnrMapper.map(pnrByPnrNumber,new Pnr()));
                bookingService.createBooking(booking);
            }
        }
    }

}
