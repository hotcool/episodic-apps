package com.example.episodicevents.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventsController {

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    public EventsController(EventRepository eventRepository, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
    }

    @PostMapping()
    public ResponseEntity<String> postEvents(@RequestBody Event event) {
        eventRepository.save(event);

        //event.add(linkTo(methodOn(EventsController.class).postEvents(event)).withSelfRel());

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/recent")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

}
