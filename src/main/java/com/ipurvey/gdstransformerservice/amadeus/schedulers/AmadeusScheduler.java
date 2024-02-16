package com.ipurvey.gdstransformerservice.amadeus.schedulers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ipurvey.gdstransformerservice.Utils.PnrMapper;
import com.ipurvey.gdstransformerservice.amadeus.client.AmadeusClientService;
import com.ipurvey.gdstransformerservice.amadeus.collections.Booking;
import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@Slf4j
public class AmadeusScheduler {


    private  final BookingService bookingService;
    private final PnrDataService pnrDataService;

    @Autowired
    public AmadeusScheduler( BookingService bookingService, PnrDataService pnrDataService) {
        this.bookingService = bookingService;
        this.pnrDataService = pnrDataService;
    }


    @Scheduled(cron = "${amadeus.scheduler.cron.expression}")
    @Transactional(rollbackFor = RuntimeException.class)
    public void amadeusScheduler() throws JsonProcessingException {
        try{
            log.info("Getting Bookings from AMADEUS");
            List<Booking> bookings = bookingService.getBookings();
            log.info("Number of records received {}", bookings.size());
            for (Booking booking : bookings) {
                Booking byBookingReference = bookingService.findByBookingReference(booking.getBookingRef());
                if(byBookingReference!=null && StringUtils.hasText(byBookingReference.getId()) && !booking.getStatus().equalsIgnoreCase(byBookingReference.getStatus())){
                    bookingService.updateBooking(byBookingReference.getBookingRef(), booking);
                    update(booking);
                }else{
                    log.info("No booking found its a new booking {}", booking.getBookingRef());
                    PnrDto pnrByPnrNumber = pnrDataService.getPnrByPnrNumber(booking.getPnr());
                    if (pnrByPnrNumber!=null)
                        pnrDataService.savePnrData(PnrMapper.map(pnrByPnrNumber,new Pnr()));
                    bookingService.createBooking(booking);
                }
            }
        }catch (RuntimeException exception){
            exception.printStackTrace();
            log.info("exception while processing records");

        }

    }

    private void update(Booking booking) throws JsonProcessingException {
        PnrDto pnrByPnrNumber = pnrDataService.getPnrByPnrNumber(booking.getPnr());
        if (pnrByPnrNumber!=null){
            Pnr pnr = PnrMapper.map(pnrByPnrNumber, new Pnr());
            pnrDataService.updatePnrData(booking.getBookingRef(),pnr  );
        }
    }

}
