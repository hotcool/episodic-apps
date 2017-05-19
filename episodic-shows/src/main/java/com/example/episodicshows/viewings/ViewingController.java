package com.example.episodicshows.viewings;

import com.example.episodicshows.users.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class ViewingController {

    private final UserRepository userRepository;

    private final ViewingService service;

    public ViewingController(UserRepository userRepository, ViewingService service) {
        this.userRepository = userRepository;
        this.service = service;
    }

    @PatchMapping("/{id}/viewings")
    public ResponseEntity patchViewing(@PathVariable long id, @RequestBody Viewing viewing) {
        if (userRepository.findOne(id) == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return service.patchViewings(id, viewing);
        }
    }

    @GetMapping("/{id}/recently-watched")
    public ResponseEntity<String> getViewings(@PathVariable long id) throws JsonProcessingException{
        if (userRepository.findOne(id) == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return service.getRecentlyWatched(id);
        }
    }

}
