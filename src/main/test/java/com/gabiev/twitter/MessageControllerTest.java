package com.gabiev.twitter;

import com.gabiev.twitter.controller.MessageController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("tuser")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/users-list-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql", "/users-list-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageController controller;

    @Test
    public void mainPageTest() throws Exception {
        mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(content().string(containsString("<div class=\"navbar-text mr-3\">tuser</div>")));
                //.andExpect(xpath("//div[@id='navbarSupportedContent']/div").string("tuser"));
                // Чтоб пользоваться xpath надо, чтоб html-документ соответсвовал xml спецификации.
                // Мне это не особо нужно и лень переписывать шаблоны. Оставил, чтобы знать.
    }

    @Test
    public void messageListTest() throws Exception {
        mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated());
                //.andExpect(кол-во сообщений в html)
    }

    @Test
    public void filterMessageTest() throws Exception {
        mockMvc.perform(get("/main").param("filter", "my-tag"))
                .andDo(print())
                .andExpect(authenticated());
                //.andExpect(кол-во сообщений с тегом my-tag в html)
    }

    @Test
    public void addMessageToListTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/main")
                .file("file", "123".getBytes())
                .param("text", "fifth")
                .param("tag", "#new one")
                .with(SecurityMockMvcRequestPostProcessors.csrf());

        mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated());
                //.andExpect(добавленное сообщение в html)
    }
}