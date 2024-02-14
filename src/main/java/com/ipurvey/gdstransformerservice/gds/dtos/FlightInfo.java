package com.ipurvey.gdstransformerservice.gds.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightInfo {

    private String channelId;
    private String customerReference;
    private String bookingEmailAddress;
    private String thirdPartyCustomerIdentification;
    private String travelMode;
    private String bookingReference;
    private String termsOfService;
    private String iPAddress;
    private String countryPhoneCode;

}
