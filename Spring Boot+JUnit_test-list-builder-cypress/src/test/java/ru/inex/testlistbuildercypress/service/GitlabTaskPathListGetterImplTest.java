package ru.inex.testlistbuildercypress.service;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.inex.testlistbuildercypress.config.AppTestConfig;
import ru.inex.testlistbuildercypress.entity.Catalog;
import ru.inex.testlistbuildercypress.entity.CatalogsList;
import ru.inex.testlistbuildercypress.entity.TaskPath;
import ru.inex.testlistbuildercypress.entity.TaskPathList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Data
@SpringBootTest(classes = AppTestConfig.class)
@ActiveProfiles("test")
class GitlabTaskPathListGetterImplTest {
    private final TaskPathListResponseBuilderImpl gitlabPathListGetter;
    @Autowired
    public GitlabTaskPathListGetterImplTest(TaskPathListResponseBuilderImpl gitlabPathListGetter) {
        this.gitlabPathListGetter = gitlabPathListGetter;
    }

    @Test
    void getPathList() {
        List<Catalog> inputList = new ArrayList<>();
        inputList.add(new Catalog("cypress/e2e/_common"));
        inputList.add(new Catalog("cypress/e2e/_noifr"));
        inputList.add(new Catalog("cypress/e2e/_themes"));
        inputList.add(new Catalog("cypress/e2e/account"));
        CatalogsList catalogsList = new CatalogsList(inputList);
        List<TaskPath> expectedList = new ArrayList<>();
        expectedList.add(new TaskPath("cypress/e2e/_common"));
        expectedList.add(new TaskPath("cypress/e2e/_noifr"));
        expectedList.add(new TaskPath("cypress/e2e/_themes"));
        expectedList.add(new TaskPath("cypress/e2e/account"));
        TaskPathList expectedCatalogList = new TaskPathList(expectedList);
        assertEquals(expectedCatalogList, gitlabPathListGetter.buildResponse(catalogsList));
    }

    @Test
    void getPathListEmptyIncomingData() {
        List<Catalog> inputList = new ArrayList<>();
        CatalogsList catalogsList = new CatalogsList(inputList);
        List<TaskPath> expectedList = new ArrayList<>();
        TaskPathList expectedCatalogList = new TaskPathList(expectedList);
        assertEquals(expectedCatalogList, gitlabPathListGetter.buildResponse(catalogsList));
    }
}