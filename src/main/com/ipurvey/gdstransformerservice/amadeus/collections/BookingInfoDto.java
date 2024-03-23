package com.ipurvey.gdstransformerservice.amadeus.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class BookingInfoDto {
    @Indexed
    private String bookingReference;
    private String bookingStatus;
    private String bookingPcc;
}
