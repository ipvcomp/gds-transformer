package com.ipurvey.gdstransformerservice.gds.factory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.ipurvey.gdstransformerservice.amadeus.collections.FlightInfoDto;
import com.ipurvey.gdstransformerservice.amadeus.collections.PassengerInfoDto;
import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
import com.ipurvey.gdstransformerservice.amadeus.collections.PnrDto;
import com.ipurvey.gdstransformerservice.gds.dtos.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GDSTransformer implements Transformer<Pnr> {
    //TODO: add topic in OCH add configs here
    @Override
    public FlightBookingRequest transform(Pnr pnr) {
        FlightBookingRequest flightBookingRequest = new FlightBookingRequest();
        FlightInfo flightInfo = new FlightInfo();

        if (pnr.getContactInfo() != null) {
            flightInfo.setBookingEmailAddress(pnr.getContactInfo().getEmail());
            flightInfo.setCountryPhoneCode(pnr.getContactInfo().getPhone() != null && pnr.getContactInfo().getPhone().length() >= 3 ? pnr.getContactInfo().getPhone().substring(0, 3) : null);
        }

        if (pnr.getBookingInfo() != null) {
            flightInfo.setBookingReference(pnr.getBookingInfo().getBookingReference());
        }

        flightInfo.setTravelMode("F");
        flightInfo.setChannelId(""+6);
        flightBookingRequest.setFlightInfo(flightInfo);

        flightBookingRequest.setTransit(pnr.getFlightInfo().size() > 2);

        List<Journey> journeys = new ArrayList<>();
        for (FlightInfoDto f : pnr.getFlightInfo()) {
            if (f == null) continue;
            Journey journey = new Journey();
            BookingDepartureDetails departureDetails = new BookingDepartureDetails();
            departureDetails.setAirportCode(f.getDepartureFrom());
            departureDetails.setAirportName(f.getDepartureAirPort());
            
            LocalDate localDate = LocalDate.parse(f.getDepartureDateLocal().replace("T",""));
            LocalTime localTime = LocalTime.parse(f.getDepartureTimeLocal().replace("T",""));
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            ZonedDateTime departureZonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
            LocalDate departureUTCDate = departureZonedDateTime.toLocalDate();
            LocalTime departureUTCTime = departureZonedDateTime.toLocalTime();
            departureDetails.setDateUTC(departureUTCDate);
            departureDetails.setTimeUTC(departureUTCTime);
            departureDetails.setDateLocal(localDate);
            departureDetails.setTimeLocal(localTime);
            BookingArrivalDetails arrivalDetails = new BookingArrivalDetails();
            arrivalDetails.setAirportCode(f.getArrivalTo());
            arrivalDetails.setAirportName(f.getArrivalAirPort());
            LocalDate arrivalLocalDate = LocalDate.parse(f.getArrivalDateLocal().replace("T",""));
            LocalTime arrivalLocalTime = LocalTime.parse(f.getArrivalTimeLocal().replace("T",""));
            LocalDateTime arrivalLocalDateTime = LocalDateTime.of(arrivalLocalDate, arrivalLocalTime);
            ZonedDateTime arrivalZonedDateTime = arrivalLocalDateTime.atZone(ZoneId.of("UTC"));
            LocalDate arrivalUTCDate = arrivalZonedDateTime.toLocalDate();
            LocalTime arrivalUTCTime = arrivalZonedDateTime.toLocalTime();
            arrivalDetails.setDateLocal(arrivalLocalDate);
            arrivalDetails.setTimeLocal(arrivalLocalTime);
            arrivalDetails.setDateUTC(arrivalUTCDate);
            arrivalDetails.setTimeUTC(arrivalUTCTime);

            journey.setCarrierCode(f.getCarrierCode());
            journey.setFlightNumber(""+f.getFlightNumber());
            journey.setLegId(""+f.getLegId());
            journey.setDepartureDetails(departureDetails);
            journey.setArrivalDetails(arrivalDetails);
            journeys.add(journey);
        }
        flightBookingRequest.setJourney(journeys);

        PassengerDetails passengerDetails = new PassengerDetails();
        if (pnr.getPassengerInfo() != null) {
            passengerDetails.setNumberOfPassengersInParty(pnr.getPassengerInfo().size());
            List<Passenger> passengers = new ArrayList<>();
            for (PassengerInfoDto passengerInfoDto : pnr.getPassengerInfo()) {
                if (passengerInfoDto == null) continue;
                Passenger passenger = new Passenger();
                passenger.setFirstName(passengerInfoDto.getGivenName());
                passenger.setLastName(passengerInfoDto.getSurName());
                passengers.add(passenger);
            }
            passengerDetails.setPassengerNames(passengers);
        }
        flightBookingRequest.setPassengers(passengerDetails);

        return flightBookingRequest;

    }
}
