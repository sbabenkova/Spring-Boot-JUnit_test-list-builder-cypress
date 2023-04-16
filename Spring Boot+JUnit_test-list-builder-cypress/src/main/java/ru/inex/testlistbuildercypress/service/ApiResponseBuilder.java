package ru.inex.testlistbuildercypress.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.inex.testlistbuildercypress.entity.ApiResponse;
import ru.inex.testlistbuildercypress.entity.TaskPathList;

/**
 * интерфейс для построения ответа контроллера/хэндлера
 */
public interface ApiResponseBuilder {
    ResponseEntity<ApiResponse<TaskPathList>> getDataResponse(TaskPathList taskPathList);
    ResponseEntity<ApiResponse<TaskPathList>> getErrorResponse(String message, HttpStatus httpStatus);
}
