package ru.inex.testlistbuildercypress.entity;

import lombok.Data;

import java.util.List;

/**
 * Обертка для данных в ответе
 */
@Data
public class TaskPathList {
    private final List<TaskPath> taskPathList;
}
