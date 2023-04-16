package ru.inex.testlistbuildercypress.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *  Обертка для ответа, как положительного, так и в случае ошибки
 */
@AllArgsConstructor
@Data
public class ApiResponse<D> {
    private Result result;
    private D data;
    private String error;
    private LocalDateTime timestamp;
}
