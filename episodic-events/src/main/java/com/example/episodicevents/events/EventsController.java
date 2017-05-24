package com.example.episodicevents.events;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventsController {

    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping()
    public ResponseEntity<String> postEvents(@RequestBody Event event) {
        eventService.insertEvent(event);

        eventService.publishEvent(event);

        //event.add(linkTo(methodOn(EventsController.class).postEvents(event)).withSelfRel());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/recent")
    public List<Event> getAllEvents() {
        return eventService.findAllEvents();
    }

}
