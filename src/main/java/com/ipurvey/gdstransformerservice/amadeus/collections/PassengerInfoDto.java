package com.ipurvey.gdstransformerservice.amadeus.collections;

import lombok.Data;

@Data
public class PassengerInfoDto {
    private String givenName;
    private String surName;
    private String gender;
    private String type;
    private String dob;
}
