
package org.example.core;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test

    @Order(1)
    public void testNewsGet() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo( "[{\"id\":1,\"title\":\"title 1\",\"details\":\"details 1\",\"tags\":" +
                                "\"tags 1\",\"reportedAt\":\"2000-01-01T12:00:00\"},{\"id\":2,\"title\":\"title 2\",\"details" +
                                "\":\"details 2\",\"tags\":\"tags 2\",\"reportedAt\":\"2000-01-01T12:00:00\"}]" )));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news/1")
                .with(SecurityMockMvcRequestPostProcessors.user("reporter"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo( "{\"id\":1,\"title\":\"title 1\",\"details\":\"details 1\",\"tags\":" +
                                "\"tags 1\",\"reportedAt\":\"2000-01-01T12:00:00\"}" )));

    }

    @Test

    @Order(3)
    public void testNewsPut() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/news/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("editor")
                                .authorities(new SimpleGrantedAuthority("EDITOR")))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"title\":\"title 3\",\"details\":\"details 3\",\"tags\":\"tags 3\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @Order(2)
    public void testNewsPost() throws Exception {

         mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                         .with(SecurityMockMvcRequestPostProcessors.user("reporter")
                                 .authorities(new SimpleGrantedAuthority("REPORTER")))
                         .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"title\":\"title 3\",\"details\":\"details 3\",\"tags\":\"tags 3\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("title 3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", Matchers.is("details 3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags", Matchers.is("tags 3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reportedAt", Matchers.notNullValue()));
    }



    @Test

    @Order(4)
    public void testNewsDelete() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/news/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("editor")
                                .authorities(new SimpleGrantedAuthority("EDITOR")))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }


}