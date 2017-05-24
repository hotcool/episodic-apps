package com.example.episodicevents.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlayEvent.class, name = "play"),
        @JsonSubTypes.Type(value = PauseEvent.class, name = "pause"),
        @JsonSubTypes.Type(value = FastForwardEvent.class, name = "fastForward"),
        @JsonSubTypes.Type(value = RewindEvent.class, name = "rewind"),
        @JsonSubTypes.Type(value = ProgressEvent.class, name = "progress"),
        @JsonSubTypes.Type(value = ScrubEvent.class, name = "scrub")
})
public class Event {

    @Id
    private String id;

    private long userId;
    private long showId;
    private long episodeId;
    private LocalDateTime createdAt;
    //private HashMap<String, Object> data;


    public Event(long userId, long showId, long episodeId, LocalDateTime createdAt) {
        this.userId = userId;
        this.showId = showId;
        this.episodeId = episodeId;
        this.createdAt = createdAt;
    }

    public String getMessageType(){
        return null;
    }
}
