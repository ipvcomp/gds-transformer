package com.ipurvey.gdstransformerservice.gds.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "flight")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@CompoundIndex(def = "{'flightNumber':1, 'travelDate':1, 'arrivalDetails.airportCode': 1}", unique = true)
public class Flight {
    @Id
    private String _id;

    private String flightNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate travelDate;
    private String flightStatus;

    private String carrierCode;
    private String carrierName;
    private Aircraft aircraft;
    private Live live;

    private ArrivalDetails arrivalDetails;
    private DepartureDetails departureDetails;
    private Airline airline;
    private FlightDetails flight;

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Live {
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime updated;
        private Double latitude;
        private Double longitude;
        private Double altitude;
        private Double direction;
        @JsonProperty("speed_horizontal")
        private Double speedHorizontal;
        @JsonProperty("speed_vertical")
        private Double speedVertical;
        @JsonProperty("is_ground")
        private Boolean isGround;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Airline {
        private String name;
        private String iata;
        private String icao;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FlightDetails {
        private String number;
        private String iata;
        private String icao;
        private Codeshared codeshared;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Codeshared {
        @JsonProperty("airline_name")
        private String airlineName;
        @JsonProperty("airline_iata")
        private String airlineIata;
        @JsonProperty("airline_icao")
        private String airlineIcao;
        @JsonProperty("flight_number")
        private String flightNumber;
        @JsonProperty("flight_iata")
        private String flightIata;
        @JsonProperty("flight_icao")
        private String flightIcao;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Aircraft {
        private String registration;
        private String iata;
        private String icao;
        private String icao24;
    }
}
