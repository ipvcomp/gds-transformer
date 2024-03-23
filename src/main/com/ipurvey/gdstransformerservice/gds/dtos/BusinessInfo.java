package com.ipurvey.gdstransformerservice.gds.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class BusinessInfo {

    private String businessType;
    private String companyName;
    private String username;
    private String email;
    private String userId;
    private String productCode;
}
