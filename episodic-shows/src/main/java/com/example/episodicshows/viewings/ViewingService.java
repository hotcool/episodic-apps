package com.example.episodicshows.viewings;

import com.example.episodicshows.shows.Episode;
import com.example.episodicshows.shows.EpisodeRepository;
import com.example.episodicshows.shows.Show;
import com.example.episodicshows.shows.ShowRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ViewingService {

    private final ViewingRepository viewingRepository;

    private final EpisodeRepository episodeRepository;

    private final ShowRepository showRepository;

    private final ObjectMapper mapper;

    public ViewingService(ViewingRepository viewingRepository, EpisodeRepository episodeRepository, ShowRepository showRepository, ObjectMapper mapper) {
        this.viewingRepository = viewingRepository;
        this.episodeRepository = episodeRepository;
        this.showRepository = showRepository;
        this.mapper = mapper;
    }

    public ResponseEntity patchViewings(long id, Viewing viewing) {
        long episodeId = viewing.getEpisodeId();
        viewingRepository.save(new Viewing(episodeRepository.findOne(episodeId).getShowId(),
                id, episodeId, viewing.getUpdatedAt(), viewing.getTimecode()));
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<String> getRecentlyWatched(long id) throws JsonProcessingException {
        List<Viewing> viewings = viewingRepository.findAllByUserIdOrderByUpdatedAtDesc(id);
        if (viewings == null || viewings.isEmpty()) {
            return new ResponseEntity<>("[]", HttpStatus.OK);
        } else {
            Map<Long, Show> showMap = new HashMap<>();
            showRepository.findAll().forEach(show -> showMap.put(show.getId(), show));

            Map<Long, Episode> episodeMap = new HashMap<>();
            episodeRepository.findAll().forEach(episode -> episodeMap.put(episode.getId(), episode));

            List<Map<String, Object>> result = new ArrayList<>();

            viewings.forEach(viewing -> {
                Map<String, Object> watchMap = new HashMap<>();

                watchMap.put("show", showMap.get(viewing.getShowId()));
                watchMap.put("episode", episodeMap.get(viewing.getEpisodeId()));
                watchMap.put("updatedAt", viewing.getUpdatedAt());
                watchMap.put("timecode", viewing.getTimecode());

                result.add(watchMap);
            });

            return new ResponseEntity<>(mapper.writeValueAsString(result), HttpStatus.OK);
        }
    }

}
