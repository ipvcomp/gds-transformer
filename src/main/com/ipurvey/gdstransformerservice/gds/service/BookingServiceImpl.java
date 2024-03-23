package com.ipurvey.gdstransformerservice.gds.service;


import com.ipurvey.gdstransformerservice.amadeus.client.AmadeusClientService;
import com.ipurvey.gdstransformerservice.amadeus.collections.Booking;
import com.ipurvey.gdstransformerservice.amadeus.repo.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {


    private final BookingRepository bookingRepository;
    private final AmadeusClientService amadeusClientService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, AmadeusClientService amadeusClientService, AmadeusClientService amadeusClientService1) {
        this.bookingRepository = bookingRepository;
        this.amadeusClientService = amadeusClientService1;
    }


    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
     public Booking findByBookingReference(String  bookingReference) {
        log.info("getting booking from db {} ", bookingReference);
        return bookingRepository.findByBookingReference(bookingReference);
    }



    // Update operation
    public Booking updateBooking(String id, Booking newBooking) {
         Booking optionalBooking = bookingRepository.findByBookingReference(id);
        if (optionalBooking!=null) {
            Booking existingBooking = optionalBooking;
            existingBooking.setBookingRef(newBooking.getBookingRef());
            existingBooking.setPCC(newBooking.getPCC());
            existingBooking.setEmail(newBooking.getEmail());
            existingBooking.setPhone(newBooking.getPhone());
            existingBooking.setPnr(newBooking.getPnr());
            existingBooking.setStatus(newBooking.getStatus());
            existingBooking.setAdult(newBooking.getAdult());
            existingBooking.setChild(newBooking.getChild());
            existingBooking.setInfant(newBooking.getInfant());
            existingBooking.setFlightDate(newBooking.getFlightDate());
            return bookingRepository.save(existingBooking);

        }
        return newBooking;
    }

    @Override
    public List<Booking> getBookings() {
        return amadeusClientService.getBookings();
    }


}
