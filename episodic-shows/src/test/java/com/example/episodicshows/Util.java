package com.example.episodicshows;

import com.example.episodicshows.shows.Episode;
import com.example.episodicshows.shows.EpisodeRepository;
import com.example.episodicshows.shows.Show;
import com.example.episodicshows.shows.ShowRepository;
import com.example.episodicshows.users.User;
import com.example.episodicshows.users.UserRepository;
import com.example.episodicshows.viewings.Viewing;
import com.example.episodicshows.viewings.ViewingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;

@Component
public class Util {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ViewingRepository viewingRepository;

    private Show show;

    private Episode episode;

    private User user;

    private Viewing viewing;

    public void setup() {
        showRepository.deleteAll();
        episodeRepository.deleteAll();
        userRepository.deleteAll();
        viewingRepository.deleteAll();

        show = new Show("bigBang");

        showRepository.save(show);

        episode = new Episode(show.getId(), 2, 3);

        episodeRepository.save(episode);

        user = new User("johnSmith@example.com");

        userRepository.save(user);

        viewing = new Viewing(show.getId(), user.getId(), episode.getId(),
                LocalDateTime.now(), 25);

        viewingRepository.save(viewing);
    }

    public Show getShow() {
        return show;
    }

    public Episode getEpisode() {
        return episode;
    }

    public User getUser() {
        return user;
    }

    public Viewing getViewing() {
        return viewing;
    }
}
