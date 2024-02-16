package com.ipurvey.gdstransformerservice.gds.service;

import com.ipurvey.gdstransformerservice.amadeus.collections.Booking;

import java.util.List;

public interface BookingService {

    Booking createBooking(Booking booking);

    Booking findByBookingReference(String bookingReference);

    Booking updateBooking(String id, Booking newBooking);

    List<Booking> getBookings();


}
