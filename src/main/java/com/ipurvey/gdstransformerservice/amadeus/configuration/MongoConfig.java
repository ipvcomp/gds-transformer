package com.ipurvey.gdstransformerservice.amadeus.configuration;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Autowired
    MongoClient mongoClient;

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate primaryMongoTemplate = new MongoTemplate(mongoClient, "gds-transformer-staging");
        primaryMongoTemplate.setSessionSynchronization(SessionSynchronization.ALWAYS);
        return primaryMongoTemplate;
    }
}
