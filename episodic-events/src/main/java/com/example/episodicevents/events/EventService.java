package com.example.episodicevents.events;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final RabbitTemplate rabbitTemplate;

    public EventService(EventRepository eventRepository, RabbitTemplate rabbitTemplate) {
        this.eventRepository = eventRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void insertEvent(Event event) {
        eventRepository.save(event);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public void publishEvent(Event event) {
        if (event.getMessageType() != null && "Progress".equalsIgnoreCase(event.getMessageType())) {
            ViewingMessage message = getMessage((ProgressEvent) event);
            rabbitTemplate.convertAndSend(EventConstant.EXCHANGE, EventConstant.ROUTING_KEY, message);
        }
    }

    private ViewingMessage getMessage(ProgressEvent event) {
        return new ViewingMessage(event.getUserId(), event.getEpisodeId(), event.getCreatedAt(), event.getData().get("offset").intValue());
    }
}
