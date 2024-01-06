package com.gabiev.twitter;

import com.gabiev.twitter.controller.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController controller;

    @Test
    public void contextLoads() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Привет, гость")))
                .andExpect(content().string(containsString("Please, login")));
    }

    @Test
    public void accessDeniedTest() throws Exception {
        mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @Sql(value = {"/users-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/users-list-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void correctLoginTest() throws Exception {
        mockMvc.perform(formLogin().user("tuser").password("1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void badCredentials() throws Exception {
        mockMvc.perform(post("/login").param("user", "unknown"))    //независимо от параметра, ответ будет forbidden (нет смысла в этом тесте)
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
