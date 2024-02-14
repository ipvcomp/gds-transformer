package com.ipurvey.gdstransformerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GdstransformerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GdstransformerServiceApplication.class, args);
	}

}
