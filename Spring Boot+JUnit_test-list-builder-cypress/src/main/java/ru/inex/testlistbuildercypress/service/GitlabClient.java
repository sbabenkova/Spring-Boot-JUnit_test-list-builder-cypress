package ru.inex.testlistbuildercypress.service;

import ru.inex.testlistbuildercypress.entity.CatalogsList;

/**
 * Интерфейс, возвращащий список каталогов, в которых лежат тесты с указанными входными параметрами в гитлаб
 */
public interface GitlabClient {
    CatalogsList getTestCatalogsList(long groupIdGitlab, String gitRepositoryRef);
}
