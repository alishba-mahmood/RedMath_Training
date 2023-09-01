
package org.example.account;
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
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test

    @Order(1)
    public void testAccountsGet() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/all")
                .with(SecurityMockMvcRequestPostProcessors.user("admin")
                        .authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo( "[[{\"password\":\"{noop}ali123\",\"name\":\"ali\",\"email\":\"ali@gmail.com\",\"address\":\"lahore\",\"roles\":\"ACCOUNT_HOLDER\",\"id\":1}," +
                                "{\"password\":\"{noop}ahmad123\",\"name\":\"ahmad\",\"email\":\"ahmad@gmail.com\",\"address\":\"karachi\",\"roles\":\"ACCOUNT_HOLDER\",\"id\":2}," +
                                "{\"password\":\"{noop}admin\",\"name\":\"admin\",\"email\":\"admin@gmail.com\",\"address\":\"karachi\",\"roles\":\"ADMIN\",\"id\":3}]," +
                                "[{\"balance_id\":1,\"account_id\":1,\"description\":\"Description1\",\"amount\":1000,\"id\":1,\"date\":\"2000-01-01\",\"db_CR\":1}," +
                                "{\"balance_id\":2,\"account_id\":2,\"description\":\"Description2\",\"amount\":2000,\"id\":2,\"date\":\"2023-01-01\",\"db_CR\":0}]," +
                                "[{\"account_id\":1,\"amount\":10000,\"date\":\"1999-12-31T19:00:00.000+00:00\",\"id\":1,\"db_CR\":1}," +
                                "{\"account_id\":2,\"amount\":20000,\"date\":\"2022-12-31T19:00:00.000+00:00\",\"id\":2,\"db_CR\":0}]]" )));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo( "[{\"password\":\"{noop}ali123\",\"name\":\"ali\",\"email\":\"ali@gmail.com\",\"address\":\"lahore\",\"roles\":\"ACCOUNT_HOLDER\",\"id\":1}," +
                                "{\"password\":\"{noop}ahmad123\",\"name\":\"ahmad\",\"email\":\"ahmad@gmail.com\",\"address\":\"karachi\",\"roles\":\"ACCOUNT_HOLDER\",\"id\":2}," +
                                "{\"password\":\"{noop}admin\",\"name\":\"admin\",\"email\":\"admin@gmail.com\",\"address\":\"karachi\",\"roles\":\"ADMIN\",\"id\":3}]" )));

            }

   @Test

    @Order(3)
    public void testNewsPut() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(new SimpleGrantedAuthority("ADMIN")))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"name\":\"ali\",\"address\":\"karachi\",\"email\":\"ali123@gmail.com\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @Order(2)
    public void testNewsPost() throws Exception {

         mockMvc.perform(MockMvcRequestBuilders.post("/api")
                         .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                 .authorities(new SimpleGrantedAuthority("ADMIN")))
                         .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"password\":\"{noop}ahsan123\",\n" +
                                "    \"name\":\"ahsan\",\n" +
                                "    \"email\":\"ahsan@gmail.com\",\n" +
                                "    \"address\":\"karachi\",\n" +
                                "    \"roles\":\"ACCOUNT_HOLDER\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("{noop}ahsan123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("ahsan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("ahsan@gmail.com")))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.roles", Matchers.is("ACCOUNT_HOLDER")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is("karachi")));
    }

    @Test
    @Order(4)
    public void testNewsDelete() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(new SimpleGrantedAuthority("ADMIN")))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

}