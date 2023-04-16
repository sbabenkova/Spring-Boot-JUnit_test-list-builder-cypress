
package ru.inex.testlistbuildercypress.service;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.inex.testlistbuildercypress.entity.Catalog;
import ru.inex.testlistbuildercypress.entity.CatalogsList;
import ru.inex.testlistbuildercypress.entity.TaskPath;
import ru.inex.testlistbuildercypress.entity.TaskPathList;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data

public class TaskPathListResponseBuilderImpl implements TaskPathListResponseBuilder {
    /**
     * Метод, возвращащий список каталогов, в которых лежат тесты с указанными входными параметрами в гитлаб
     * Переоборачиват ответ гитлаба в нужный для Accepter'а формат
     * @param catalogsList список каталогов в виде объекта CatalogsList (List String)
     * @return ответ, содержащий список каталогов TaskPathList
     */
    @Override
    public TaskPathList buildResponse(CatalogsList catalogsList) {
        List<TaskPath> taskPaths = catalogsList.getCatalog().stream()
                .map(Catalog::getPath).map(TaskPath::new)
                .collect(Collectors.toList());
        return new TaskPathList(taskPaths);
    }
}

