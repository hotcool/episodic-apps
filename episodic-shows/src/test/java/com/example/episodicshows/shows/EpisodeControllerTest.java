package com.example.episodicshows.shows;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
@SpringBootTest
public class EpisodeControllerTest {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private MockMvc mockMvc;

    private Show show;

    private Episode episode;

    @Before
    public void setup() {
        showRepository.deleteAll();
        episodeRepository.deleteAll();

        show = new Show("name");

        showRepository.save(show);

        episode = new Episode(show.getId(), 2, 3);

        episodeRepository.save(episode);
    }

    @Test
    @Transactional
    @Rollback
    public void testPostShow() throws Exception {
        MockHttpServletRequestBuilder request = post(String.format("/shows/%d/episodes", show.getId()))
                .content("{\"seasonNumber\": 1,\"episodeNumber\": 2}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.seasonNumber", is(1)))
                .andExpect(jsonPath("$.episodeNumber", is(2)))
                .andExpect(jsonPath("$.title", is("S1 E2")))
        ;
    }

    @Test
    @Transactional
    @Rollback
    public void testPostShowWith400() throws Exception {
        MockHttpServletRequestBuilder request = post("/shows/5432/episodes")
                .content("{\"seasonNumber\": 1,\"episodeNumber\": 2}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Rollback
    public void testGetShow() throws Exception {
        mockMvc.perform(get(String.format("/shows/%d/episodes", show.getId()))).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].seasonNumber", is(2)))
                .andExpect(jsonPath("$[0].episodeNumber", is(3)))
                .andExpect(jsonPath("$[0].title", is("S2 E3")));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetShowWith404() throws Exception {
        mockMvc.perform(get("/shows/543707/episodes")).andExpect(status().isNotFound());
    }
}
