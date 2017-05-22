package com.example.episodicevents.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class RewindEvent extends Event {

    private HashMap<String, Object> data;

    public RewindEvent(long userId, long showId, long episodeId, LocalDateTime createdAt, HashMap<String, Object> data) {
        super(userId, showId, episodeId, createdAt);
        this.data = data;
    }

    public String getType() {
        return "rewind";
    }
}
