package ru.inex.testlistbuildercypress.entity;

import lombok.Data;

import java.util.List;

/**
 * Обертка для ответа гитлаба
 */
@Data
public class CatalogsList {
    private final List<Catalog> catalog;
}
