package com.ipurvey.gdstransformerservice.gds.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "dcr_journey_response")
public class DCRJourneyResponse {

    @Id private String id;

    private String travelMode;
    private String bookingEmailAddress;
    private String bookingReference;
    private boolean existingRecord;
    private String channelId;
    private String instructionIdentification;
    private Journey journey;
    private List<Journey> transitJourney;
    private EVTResponse evtResponse;
    private List<EVTResponse> transitEvtResponses = new ArrayList<>();
    private BRCResponse brcResponse;
    private OCHResponse ochResponse;
    private String correspondenceAddressAvailability;
    private String socketId;
    private Boolean transit;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate scheduledArrivalDate;

    @JsonFormat(pattern = "HH:mm")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime scheduledArrivalTime;
    private String batchNo;
    private String businessType;
    private String companyName;

    @Data
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OCHResponse {
        private String responseCode;
        private String description;
    }
}
