package com.ipurvey.gdstransformerservice;

import com.ipurvey.gdstransformerservice.amadeus.configuration.AmadeusConfigurations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(value = {AmadeusConfigurations.class})
public class GdstransformerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GdstransformerServiceApplication.class, args);
	}

}
