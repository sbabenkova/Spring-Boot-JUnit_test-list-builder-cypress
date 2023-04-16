package ru.inex.testlistbuildercypress.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.inex.testlistbuildercypress.entity.ApiResponse;
import ru.inex.testlistbuildercypress.entity.TaskPathList;
import ru.inex.testlistbuildercypress.exception.DataNotFoundException;
import ru.inex.testlistbuildercypress.service.ApiResponseBuilder;

import static ru.inex.testlistbuildercypress.Constants.INTERNAL_SERVER_ERROR;

/**
 * Описание всех исключений, используемых в сервисе
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final ApiResponseBuilder apiResponseBuilder;

    public GlobalExceptionHandler(@Autowired ApiResponseBuilder apiResponseBuilder) {
        this.apiResponseBuilder = apiResponseBuilder;
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiResponse<TaskPathList>> dataNotFound(DataNotFoundException dataNotFoundException) {
        log.error(dataNotFoundException.getMessage());
        return apiResponseBuilder.getErrorResponse(dataNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiResponse<TaskPathList>> internalServerError(Throwable throwable) {
        log.error(throwable.getMessage());
        return apiResponseBuilder.getErrorResponse(INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
