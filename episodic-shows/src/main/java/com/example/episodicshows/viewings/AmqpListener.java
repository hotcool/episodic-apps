package com.example.episodicshows.viewings;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import javax.transaction.Transactional;

@Configuration
public class AmqpListener implements RabbitListenerConfigurer {

    private final ViewingService service;

    public AmqpListener(ViewingService service) {
        this.service = service;
    }

    @RabbitListener(queues = "episodic-shows")
    @Transactional
    public void receiveMessage(final ViewingMessage message) {
        System.out.println("************************************************");
        System.out.println(message.toString());
        service.updateViewing(message);
        System.out.println("************************************************");
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}
