package com.ipurvey.gdstransformerservice.amadeus.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collation = "pnr_data")
public class PnrData extends BaseEntity {
    private List<PnrInfo> pnrData;

    private ProcessingStatus processingStatus=ProcessingStatus.PENDING;
    public PnrData(List<PnrInfo> pnrData) {
        this.pnrData = pnrData;
    }

    public List<PnrInfo> getPnrData() {
        return pnrData;
    }

    public void setPnrData(List<PnrInfo> pnrData) {
        this.pnrData = pnrData;
    }

    @Data
    public static class PnrInfo {
        private String id;
        @Indexed
        private BookingInfo bookingInfo;
        @Indexed
        private ContactInfo contactInfo;
        private List<PassengerInfo> passengerInfo;
        private List<FlightInfo> flightInfo;

    }

    @Data
    public static class BookingInfo {
        @Indexed
        private String bookingReference;
        private String bookingStatus;
        private String bookingPcc;

    }

    @Data
    public static class ContactInfo {
        @Indexed
        private String email;
        private String phone;

    }

    @Data
    public static class PassengerInfo {
        private String givenName;
        private String surName;
        private String gender;
        private String type;
        private String dob;


    }

    @Data
    public static class FlightInfo {
        @Indexed
        private int legId;
        private String carrierCode;
        @Indexed
        private int flightNumber;
        private String departureFrom;
        private String departureAirPort;
        private String departureDateLocal;
        private String departureTimeLocal;
        private String arrivalTo;
        private String arrivalAirPort;
        private String arrivalDateLocal;
        private String arrivalTimeLocal;

    }
}
