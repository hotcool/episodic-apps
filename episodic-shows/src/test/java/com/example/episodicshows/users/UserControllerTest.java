package com.example.episodicshows.users;

import com.example.episodicshows.MyTestBaseClass;
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
public class UserControllerTest extends MyTestBaseClass {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @Before
    public void setup() {
        userRepository.deleteAll();

        user = new User("johnSmith@example.com");

        userRepository.save(user);
    }

    @Test
    @Transactional
    @Rollback
    public void testPost() throws Exception {
        MockHttpServletRequestBuilder request = post("/users")
                .content("{\"email\": \"joe@example.com\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email", is("joe@example.com")));
    }

    @Test
    @Transactional
    @Rollback
    public void testGet() throws Exception {
        mockMvc.perform(get("/users")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].email", is("johnSmith@example.com")));
    }
}
