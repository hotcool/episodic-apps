package com.example.episodicshows.shows;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowRepository showRepository;

    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @PostMapping
    public ResponseEntity<Show> createShow(@RequestBody Show show){
        return new ResponseEntity<>(showRepository.save(show), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Show>> getShows(){
        return new ResponseEntity<>(showRepository.findAll(), HttpStatus.OK);
    }

}
