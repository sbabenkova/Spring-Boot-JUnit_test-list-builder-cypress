package ru.inex.testlistbuildercypress.service;

import ru.inex.testlistbuildercypress.entity.CatalogsList;
import ru.inex.testlistbuildercypress.entity.TaskPathList;

/**
 * Интерфейс, преобразующий данные из гитлаба в данные для Accepter'а
 */
public interface TaskPathListResponseBuilder {
    TaskPathList buildResponse(CatalogsList catalogsList);
}
