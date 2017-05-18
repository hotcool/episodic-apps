package com.example.episodicshows.shows;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "episodes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long showId;

    private int seasonNumber;
    private int episodeNumber;

    public Episode(long showId, int seasonNumber, int episodeNumber) {
        this.showId = showId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
    }
}
