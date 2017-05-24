package com.example.episodicevents.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class ProgressEvent extends Event {

    private HashMap<String, Long> data;

    public ProgressEvent(long userId, long showId, long episodeId, LocalDateTime createdAt, HashMap<String, Long> data) {
        super(userId, showId, episodeId, createdAt);
        this.data = data;
    }

    public String getMessageType() {
        return "progress";
    }
}
