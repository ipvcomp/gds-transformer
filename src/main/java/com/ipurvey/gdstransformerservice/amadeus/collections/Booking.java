package com.ipurvey.gdstransformerservice.amadeus.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "bookings")
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "version"})
public class Booking extends BaseEntity {
    @Id
    @JsonProperty(value = "_id")
    @JsonIgnore
    private String id;

    @Indexed(unique = true)
    private String bookingRef;

    @Indexed
    private String PCC;

    @Indexed
    private String email;

    @Indexed
    private String phone;

    @Indexed
    private String pnr;
    private String status;
    private int adult;
    private int child;
    private int infant;
    private Date flightDate;



}
