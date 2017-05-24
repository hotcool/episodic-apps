package com.example.episodicevents.events;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ViewingMessage {

    private Long userId;
    private Long episodeId;
    private LocalDateTime createdAt;
    private int offset;

}
