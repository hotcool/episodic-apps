package com.example.episodicevents.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpPublisher {

    private final ObjectMapper objectMapper;

    public AmqpPublisher(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EventConstant.EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue("my-publish-queue");
    }

    @Bean
    public Binding declareBinding() {
        return BindingBuilder.bind(queue()).to(appExchange()).with(EventConstant.ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(objectMapper);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

}