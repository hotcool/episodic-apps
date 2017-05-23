package com.example.episodicshows.viewings;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ViewingRepository extends CrudRepository<Viewing, Long> {

    List<Viewing> findByEpisodeId(long episodeId);

    //@Query(value = "SELECT * from viewings where user_id = ?1 order by updated_at desc", nativeQuery = true)
    List<Viewing> findAllByUserIdOrderByUpdatedAtDesc(long userId);

    Viewing findByEpisodeIdAndUserId(long episodeId, long userId);

}
