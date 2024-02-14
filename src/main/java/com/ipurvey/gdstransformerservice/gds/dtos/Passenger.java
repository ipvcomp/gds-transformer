package com.ipurvey.gdstransformerservice.gds.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Passenger {
    private String passengerId;
    private String firstName;
    private String lastName;
}