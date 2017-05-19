package com.example.episodicshows.shows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shows")
public class EpisodeController {

    private final ShowRepository showRepository;

    private final EpisodeRepository episodeRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    public EpisodeController(ShowRepository showRepository, EpisodeRepository episodeRepository) {
        this.showRepository = showRepository;
        this.episodeRepository = episodeRepository;
    }

    @PostMapping("/{id}/episodes")
    public ResponseEntity<String> postEpisode(@PathVariable long id, @RequestBody Episode episode) throws JsonProcessingException {
        Show show = showRepository.findOne(id);
        if (show != null) {
            episode.setShowId(show.getId());
            Episode episodeInserted = episodeRepository.save(episode);
            Map<String, Object> resultMap = getMapFromEpisode(episodeInserted);

            return new ResponseEntity<>(mapper.writeValueAsString(resultMap), HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Error! This episode is not in shows!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/episodes")
    public ResponseEntity<String> getEpisodes(@PathVariable long id) throws JsonProcessingException {
        List<Episode> episodes = episodeRepository.findByShowId(id);
        if (episodes != null && episodes.size() > 0) {
            List<Map<String, Object>> episodesResult = new ArrayList<>();
            episodes.forEach(e -> {
                Map<String, Object> map = getMapFromEpisode(e);
                episodesResult.add(map);
            });

            return new ResponseEntity<>(mapper.writeValueAsString(episodesResult), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error! This episode is not in shows!", HttpStatus.NOT_FOUND);
        }
    }

    private Map<String, Object> getMapFromEpisode(Episode episode) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", episode.getId());
        resultMap.put("seasonNumber", episode.getSeasonNumber());
        resultMap.put("episodeNumber", episode.getEpisodeNumber());
        resultMap.put("title", String.format("S%d E%d", episode.getSeasonNumber(), episode.getEpisodeNumber()));
        return resultMap;
    }

}
