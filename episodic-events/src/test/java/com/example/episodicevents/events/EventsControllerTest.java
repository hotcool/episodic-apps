package com.example.episodicevents.events;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
public class EventsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    private final String playPayload = "{\"type\": \"play\",\"userId\": 52,\"showId\": 987,\"episodeId\": 456,\"createdAt\": \"2017-11-08T15:59:13.0091745\",\"data\": {\"offset\": 0}}";
    private final String pausePayload = "{\"type\":\"pause\",\"userId\":52,\"showId\":987,\"episodeId\":456,\"createdAt\":\"2017-11-08T15:59:13.0091745\",\"data\":{\"offset\":1023}}";
    private final String fastForwardPayload = "{\"type\":\"fastForward\",\"userId\":52,\"showId\":987,\"episodeId\":456,\"createdAt\":\"2017-11-08T15:59:13.0091745\",\"data\":{\"startOffset\":4,\"endOffset\":408,\"speed\":2.5}}";
    private final String rewindPayload = "{\"type\":\"rewind\",\"userId\":52,\"showId\":987,\"episodeId\":456,\"createdAt\":\"2017-11-08T15:59:13.0091745\",\"data\":{\"startOffset\":4,\"endOffset\":408,\"speed\":2.5}}";
    private final String progressPayload = "{\"type\":\"progress\",\"userId\":52,\"showId\":987,\"episodeId\":456,\"createdAt\":\"2017-11-08T15:59:13.0091745\",\"data\":{\"offset\":4}}";
    private final String scrubPayload = "{\"type\":\"scrub\",\"userId\":52,\"showId\":987,\"episodeId\":456,\"createdAt\":\"2017-11-08T15:59:13.0091745\",\"data\":{\"startOffset\":4,\"endOffset\":408}}";

    @Before
    public void setup() {
        eventRepository.deleteAll();

        HashMap<String, Long> data = new HashMap<>();
        data.put("offset", 3L);
        PlayEvent playEvent = new PlayEvent(52, 123, 666, LocalDateTime.now(), data);
        eventRepository.save(playEvent);
    }

    @Test
    public void testPostPlayEvent() throws Exception {
        MockHttpServletRequestBuilder request = post("/")
                .content(playPayload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testPostPauseEvent() throws Exception {
        MockHttpServletRequestBuilder request = post("/")
                .content(pausePayload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testPostFastForwardEvent() throws Exception {
        MockHttpServletRequestBuilder request = post("/")
                .content(fastForwardPayload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testPostRewindEvent() throws Exception {
        MockHttpServletRequestBuilder request = post("/")
                .content(rewindPayload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testPostProgressEvent() throws Exception {
        MockHttpServletRequestBuilder request = post("/")
                .content(progressPayload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testPostScrubEvent() throws Exception {
        MockHttpServletRequestBuilder request = post("/")
                .content(scrubPayload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/recent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("play")))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].userId", is(52)))
                .andExpect(jsonPath("$[0].showId", is(123)))
                .andExpect(jsonPath("$[0].episodeId", is(666)))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].data.offset", is(3)))
        ;
    }

}
