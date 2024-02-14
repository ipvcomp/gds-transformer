package com.ipurvey.gdstransformerservice.gds.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BRCResponse {
    private boolean valid;
    private String routeValidityId;
    private String responseCode;
    private List<String> description;

}
