package ru.inex.testlistbuildercypress.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.inex.testlistbuildercypress.config.AppTestConfig;
import ru.inex.testlistbuildercypress.entity.TaskPath;
import ru.inex.testlistbuildercypress.entity.TaskPathList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = AppTestConfig.class)
@ActiveProfiles("test")
class TestListBuilderCypressServiceTest {
    private final TestListBuilderCypressService testListBuilderCypressService;

    @Autowired
    public TestListBuilderCypressServiceTest(TestListBuilderCypressService testListBuilderCypressService) {
        this.testListBuilderCypressService = testListBuilderCypressService;
    }

    @Test
    void getPathList() {
        TaskPathList taskPathList = testListBuilderCypressService.getPathList(447, "release-rzd");
        List<TaskPath> expectedList = new ArrayList<>();
        expectedList.add(new TaskPath("cypress/e2e/_common"));
        assertNotNull(taskPathList);
        assertTrue(taskPathList.getTaskPathList().containsAll(expectedList));
    }
}