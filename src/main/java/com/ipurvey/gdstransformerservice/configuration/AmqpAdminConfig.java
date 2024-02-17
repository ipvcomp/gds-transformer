package com.ipurvey.gdstransformerservice.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AmqpAdminConfig {
    public static final String EXCHANGE_NAME = "gds_exchange";
    //convention ipurvey_sendingService_gdsName_receivingName_communicationType
    public static final String GDS_AMADEUS_TO_OCH_QUEUE = "ipurvey.gds_tranformer_amadeus_och.oneway";
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }
    @Bean
    Queue gdsAmadeusToOchQueue() {
        return new Queue(GDS_AMADEUS_TO_OCH_QUEUE, true);
    }


    @Bean
    public Binding gdsAmadeusToOchQueueBinding(Exchange delayExchange) {
        return BindingBuilder.bind(gdsAmadeusToOchQueue()).to(delayExchange).with(GDS_AMADEUS_TO_OCH_QUEUE).noargs();
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
