package com.ipurvey.gdstransformerservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ipurvey.gdstransformerservice.amadeus.configuration.AmadeusConfigurations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(value = {AmadeusConfigurations.class})
public class GdstransformerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GdstransformerServiceApplication.class, args);
	}
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		Jackson2ObjectMapperBuilder builder =
				new Jackson2ObjectMapperBuilder()
						.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
						.serializers(
								new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")))
						.serializationInclusion(JsonInclude.Include.NON_NULL);
		return new MappingJackson2HttpMessageConverter(builder.build());
	}
}
