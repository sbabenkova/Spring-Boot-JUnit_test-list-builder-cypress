package ru.inex.testlistbuildercypress.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.inex.testlistbuildercypress.config.AppTestConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AppTestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TestListBuilderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPathList() throws Exception {
        mockMvc.perform(get("/tests/getTestPathListFromGitlab").param("projectIdGitlab", "447").param("ref", "release-rzd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.taskPathList[0].taskPath").value("cypress/e2e/_common"));
    }

    @Test
    void getPathListGitlabWrongResponseStatus() throws Exception {
        mockMvc.perform(get("/tests/getTestPathListFromGitlab").param("projectIdGitlab", "0").param("ref", "test"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.result").value("FAIL"));
    }

    @Test
    void getPathListGitlabDataNotFound() throws Exception {
        mockMvc.perform(get("/tests/getTestPathListFromGitlab").param("projectIdGitlab", "518").param("ref", "release-rzd"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.result").value("FAIL"));
    }

}