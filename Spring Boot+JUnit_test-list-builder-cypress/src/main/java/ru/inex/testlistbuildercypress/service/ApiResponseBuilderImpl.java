package ru.inex.testlistbuildercypress.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.inex.testlistbuildercypress.entity.ApiResponse;
import ru.inex.testlistbuildercypress.entity.Result;
import ru.inex.testlistbuildercypress.entity.TaskPathList;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@Data
@Slf4j
public class ApiResponseBuilderImpl implements ApiResponseBuilder {
    /**
     * Формат, в котором выводить корректный ответ сервиса
     * @param taskPathList ответ сервиса
     * @return ответ, содержащий json с корректными данными
     */
    public ResponseEntity<ApiResponse<TaskPathList>> getDataResponse(TaskPathList taskPathList) {
        ApiResponse<TaskPathList> apiResponse = new ApiResponse<>(Result.OK, taskPathList, "", LocalDateTime.now());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    /**
     * Формат, в котором выводить некорректный ответ сервиса
     *
     * @param message    сообщение, возвращаемое в отрицательном ответе
     * @param httpStatus статускод исключения, выбрасываемого сервисом
     * @return ответ, содержащий json с некорректными данными
     */
    public ResponseEntity<ApiResponse<TaskPathList>> getErrorResponse(String message, HttpStatus httpStatus) {
        ApiResponse<TaskPathList> apiResponse = new ApiResponse<>(Result.FAIL, new TaskPathList(new ArrayList<>()), message, LocalDateTime.now());
        return new ResponseEntity<>(apiResponse, httpStatus);
    }
}
