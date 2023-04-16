package ru.inex.testlistbuildercypress.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.inex.testlistbuildercypress.entity.CatalogsList;
import ru.inex.testlistbuildercypress.entity.TaskPathList;

/**
 * Основной сервис приложения.
 */
@Data
@Service
@Slf4j
public class TestListBuilderCypressService implements TestListBuilderService{
    private GitlabClient gitlabClient;
    private TaskPathListResponseBuilder taskPathListResponseBuilder;

    @Autowired
    public TestListBuilderCypressService(GitlabClient gitlabClient, TaskPathListResponseBuilder taskPathListResponseBuilder) {
        this.gitlabClient = gitlabClient;
        this.taskPathListResponseBuilder = taskPathListResponseBuilder;
    }

    /**
     *
     * @param projectIdGitlab id проекта в гитлабе
     * @param gitRepositoryRef ветка проекта
     * @return объект, нужный Accepter'у
     */
    @Override
    public TaskPathList getPathList(long projectIdGitlab, String gitRepositoryRef) {
        CatalogsList catalogsList = gitlabClient.getTestCatalogsList(projectIdGitlab, gitRepositoryRef);
        TaskPathList taskPathList = taskPathListResponseBuilder.buildResponse(catalogsList);
        log.info(taskPathList.toString());
        return taskPathList;
    }
}
