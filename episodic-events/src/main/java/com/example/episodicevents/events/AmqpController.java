package com.example.episodicevents.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/amqp")
public class AmqpController {
    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper mapper;

    public AmqpController(RabbitTemplate rabbitTemplate, ObjectMapper mapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
    }

    @PostMapping
    public void publishEvent(@RequestBody String body) throws IOException{
        ViewingMessage message = mapper.readValue(body, ViewingMessage.class);
        rabbitTemplate.convertAndSend(EventConstant.EXCHANGE, EventConstant.ROUTING_KEY, message);
    }
}
