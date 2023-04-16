package ru.inex.testlistbuildercypress.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.inex.testlistbuildercypress.entity.ApiResponse;
import ru.inex.testlistbuildercypress.entity.TaskPathList;
import ru.inex.testlistbuildercypress.service.ApiResponseBuilder;
import ru.inex.testlistbuildercypress.service.TestListBuilderService;

@RestController
@RequestMapping("/tests")
@Data
@Slf4j

public class TestListBuilderController {

    private TestListBuilderService testListBuilderService;
    private ApiResponseBuilder apiResponseBuilder;

    public TestListBuilderController(@Autowired TestListBuilderService testListBuilderService, @Autowired ApiResponseBuilder apiResponseBuilder) {
        this.testListBuilderService = testListBuilderService;
        this.apiResponseBuilder = apiResponseBuilder;
    }
    /**
     *
     * @param projectIdGitlab id проекта в GitLab
     * @param gitRepositoryRef имя ветки
     * @return ответ, содержащий json с данными в поле data.
     */
    @GetMapping("/getTestPathListFromGitlab")
    public ResponseEntity<ApiResponse<TaskPathList>> getPathList(@RequestParam(name = "projectIdGitlab") long projectIdGitlab, @RequestParam(name = "ref") String gitRepositoryRef) {
        log.info("Request with parameters projectIdGitlab = " + projectIdGitlab + " and ref = " + gitRepositoryRef + " got");
        return apiResponseBuilder.getDataResponse(testListBuilderService.getPathList(projectIdGitlab, gitRepositoryRef));
    }
}
