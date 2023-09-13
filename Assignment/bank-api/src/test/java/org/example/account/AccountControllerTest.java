
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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo( "{\"content\":[{\"account_id\":1,\"name\":\"ali\",\"email\":\"ali@gmail.com\",\"address\":\"lahore\"}," +
                                "{\"account_id\":2,\"name\":\"ahmad\",\"email\":\"ahmad@gmail.com\",\"address\":\"karachi\"}," +
                                "{\"account_id\":3,\"name\":\"sara\",\"email\":\"sara@gmail.com\",\"address\":\"karachi\"}," +
                                "{\"account_id\":4,\"name\":\"sana\",\"email\":\"sana@gmail.com\",\"address\":\"karachi\"}]}" )));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts/all")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo( "{\"content\":[[{\"account_id\":1,\"name\":\"ali\",\"email\":\"ali@gmail.com\",\"address\":\"lahore\"}," +
                                "{\"account_id\":2,\"name\":\"ahmad\",\"email\":\"ahmad@gmail.com\",\"address\":\"karachi\"}," +
                                "{\"account_id\":3,\"name\":\"sara\",\"email\":\"sara@gmail.com\",\"address\":\"karachi\"}," +
                                "{\"account_id\":4,\"name\":\"sana\",\"email\":\"sana@gmail.com\",\"address\":\"karachi\"}]," +
                                "[{\"balance_id\":1,\"account_id\":1,\"amount\":2000,\"date\":\"2023-12-11\",\"db_CR\":\"credit\"}," +
                                "{\"balance_id\":2,\"account_id\":2,\"amount\":1000,\"date\":\"2023-12-11\",\"db_CR\":\"credit\"}," +
                                "{\"balance_id\":3,\"account_id\":3,\"amount\":5000,\"date\":\"2023-12-11\",\"db_CR\":\"credit\"}," +
                                "{\"balance_id\":4,\"account_id\":4,\"amount\":5000,\"date\":\"2023-12-11\",\"db_CR\":\"credit\"}]," +
                                "[{\"transaction_id\":1,\"balance_id\":1,\"account_id\":1,\"description\":\"withdraw money\",\"amount\":500,\"date\":\"2023-08-01\",\"db_CR\":\"debit\"}," +
                                "{\"transaction_id\":2,\"balance_id\":2,\"account_id\":2,\"description\":\"add money\",\"amount\":500,\"date\":\"2023-08-01\",\"db_CR\":\"credit\"}," +
                                "{\"transaction_id\":3,\"balance_id\":2,\"account_id\":2,\"description\":\"add money\",\"amount\":500,\"date\":\"2023-08-01\",\"db_CR\":\"credit\"}]]}" )));

    }

    @Test
    @Order(2)
    public void testNewsPut() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/accounts/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(new SimpleGrantedAuthority("ADMIN")))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"name\":\"ahsan\",\"address\":\"karachi\",\"email\":\"ali123@gmail.com\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @Order(3)
    public void testNewsPost() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(new SimpleGrantedAuthority("ADMIN")))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"name\":\"ahsan\",\"email\":\"ahsan@gmail.com\",\"address\":\"lahore\",\"id\":6}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.name", Matchers.is("ahsan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.email", Matchers.is("ahsan@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.address", Matchers.is("lahore")));
    }

}