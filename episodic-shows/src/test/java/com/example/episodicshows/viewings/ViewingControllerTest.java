package com.example.episodicshows.viewings;

import com.example.episodicshows.Util;
import com.example.episodicshows.shows.Episode;
import com.example.episodicshows.users.User;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
@SpringBootTest
public class ViewingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Util util;

    @Autowired
    private ViewingRepository viewingRepository;

    private Episode episode;

    private User user;

    private Viewing viewing;

    @Before
    public void setup() {
        util.setup();

        episode = util.getEpisode();
        user = util.getUser();
        viewing = util.getViewing();
    }

    @Test
    @Transactional
    @Rollback
    public void testPatchShowCreate404() throws Exception {
        MockHttpServletRequestBuilder request = patch("/users/1438/viewings")
                .content("{\"episodeId\": 3,\"updatedAt\": \"2017-05-18T11:45:34.9182\",\"timecode\": 79}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Rollback
    public void testPatchShowCreate200Insert() throws Exception {
        MockHttpServletRequestBuilder request = patch(String.format("/users/%d/viewings", user.getId()))
                .content(String.format("{\"episodeId\": %d,\"updatedAt\": \"2017-05-18T11:45:34.9182\",\"timecode\": 79}", episode.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback
    public void testPatchShowCreate200Update() throws Exception {
        MockHttpServletRequestBuilder request = patch(String.format("/users/%d/viewings", user.getId()))
                .content(String.format("{\"episodeId\": %d,\"updatedAt\": \"2017-05-18T11:45:34.9182\",\"timecode\": 79}", episode.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        request = patch(String.format("/users/%d/viewings", user.getId()))
                .content(String.format("{\"episodeId\": %d,\"updatedAt\": \"2017-05-18T11:45:34.9182\",\"timecode\": 9527}", episode.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback
    public void testGetShow404() throws Exception {
        mockMvc.perform(get("/users/9527/recently-watched")).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Rollback
    public void testGetShow200Empty() throws Exception {
        //need to delete from the viewings table first
        viewingRepository.deleteAll();
        MvcResult result = mockMvc.perform(get(String.format("/users/%d/recently-watched", user.getId())))
                .andExpect(status().isOk())
                .andReturn();

        String value = result.getResponse().getContentAsString();

        assertEquals("[]", value);
    }

    @Test
    @Transactional
    @Rollback
    public void testGetShow200Real() throws Exception {
        mockMvc.perform(get(String.format("/users/%d/recently-watched", user.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].show.id").exists())
                .andExpect(jsonPath("$[0].show.name", is("bigBang")))
                .andExpect(jsonPath("$[0].episode.id").exists())
                .andExpect(jsonPath("$[0].episode.seasonNumber", is(2)))
                .andExpect(jsonPath("$[0].episode.episodeNumber", is(3)))
                .andExpect(jsonPath("$[0].updatedAt").exists())
                .andExpect(jsonPath("$[0].timecode", is(25)))
        ;
    }
}
