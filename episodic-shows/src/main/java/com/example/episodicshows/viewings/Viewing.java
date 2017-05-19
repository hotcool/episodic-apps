package com.example.episodicshows.viewings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity(name = "viewings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Viewing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long showId;
    private Long userId;
    private Long episodeId;

    @Column(columnDefinition = "DATETIME(3)")
    private LocalDateTime updatedAt;
    private int timecode;

    public Viewing(Long showId, Long userId, Long episodeId, LocalDateTime updatedAt, int timecode) {
        this.showId = showId;
        this.userId = userId;
        this.episodeId = episodeId;
        this.updatedAt = updatedAt;
        this.timecode = timecode;
    }
}
