package com.ipurvey.gdstransformerservice.amadeus.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "amadeus")
@Data
public class AmadeusConfigurations {

    private int schedulerCronExpression;
    private String baseUrl;


}
