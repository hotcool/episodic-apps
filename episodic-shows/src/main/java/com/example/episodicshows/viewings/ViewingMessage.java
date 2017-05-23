package com.example.episodicshows.viewings;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ViewingMessage {

    private Long userId;
    private Long episodeId;
    private Date createdAt;
    private int offset;

}
