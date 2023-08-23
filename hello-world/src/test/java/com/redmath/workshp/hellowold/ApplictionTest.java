package com.redmath.workshp.hellowold;

import com.redmath.workshop.helloworld.Application;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Locale;
import java.util.TimeZone;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplictionTest {

    private MockMvc mvc;
    private ApplicationContext applicationContext;

    @Test
    public void testHelloWorld() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get( "/index.html"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo( "<h1>Hello world</h1>" )));
    }
}
