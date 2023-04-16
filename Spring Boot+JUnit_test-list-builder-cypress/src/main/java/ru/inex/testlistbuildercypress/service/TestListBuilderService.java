package ru.inex.testlistbuildercypress.service;

import ru.inex.testlistbuildercypress.entity.TaskPathList;
/**
 * Основной сервис приложения.
 */
public interface TestListBuilderService {
    /**
     *
     * @param projectIdGitlab id проекта в гитлабе
     * @param gitRepositoryRef ветка проекта
     * @return объект, нужный Accepter'у
     */
    TaskPathList getPathList(long projectIdGitlab, String gitRepositoryRef);
}
