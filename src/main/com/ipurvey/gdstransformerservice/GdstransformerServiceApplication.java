package com.ipurvey.gdstransformerservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import com.ipurvey.gdstransformerservice.amadeus.configuration.AmadeusConfigurations;
import com.ipurvey.gdstransformerservice.amadeus.soap.Constants;
import com.ipurvey.gdstransformerservice.amadeus.soap.handlers.ServiceHandler;
import com.ipurvey.gdstransformerservice.amadeus.soap.handlers.SessionHandler;
import com.ipurvey.gdstransformerservice.amadeus.soap.handlers.TransactionFlowLinkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(value = {AmadeusConfigurations.class})
public class GdstransformerServiceApplication {
	private final static Logger logger = LoggerFactory.getLogger(GdstransformerServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(GdstransformerServiceApplication.class, args);
		try {
			Constants.init();
		} catch(Throwable t) {
			logger.error("Failed setup due to: " + t.toString());
			return;
		}
		try {
			RunExample();
		} catch(Throwable t) {
			logger.error("Failed execution due to: " + t.toString());
			return;
		}


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



public static void RunExample() throws Exception {

//		 WSAP in SOAP header
	ServiceHandler serviceHandler = new ServiceHandler(Constants.WSAP_name);

	// Stateless call
	logger.info("Stateless call");
	serviceHandler.fareMasterPricerTravelBoardSearchReply(SessionHandler.TransactionStatusCode.NONE, TransactionFlowLinkHandler.TransactionFlowLinkAction.NONE);
//	serviceHandler.listPnrs(SessionHandler.TransactionStatusCode.NONE, TransactionFlowLinkHandler.TransactionFlowLinkAction.NONE);

//		serviceHandler.airSellFromRecommendationReply(SessionHandler.TransactionStatusCode.NONE, TransactionFlowLinkHandler.TransactionFlowLinkAction.NONE);
	logger.info("Done.");

}
}
