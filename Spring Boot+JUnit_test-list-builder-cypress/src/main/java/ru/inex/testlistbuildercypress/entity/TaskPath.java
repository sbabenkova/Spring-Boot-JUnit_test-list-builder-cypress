package ru.inex.testlistbuildercypress.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Обертка для поля ответа с данными
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPath {
    private String taskPath;
}
