package ru.inex.testlistbuildercypress.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.inex.testlistbuildercypress.entity.Catalog;
import ru.inex.testlistbuildercypress.entity.CatalogsList;
import ru.inex.testlistbuildercypress.exception.DataNotFoundException;
import ru.inex.testlistbuildercypress.exception.GitlabResponseException;

import java.util.Arrays;

@Component
@Data
@Slf4j
public class GitlabClientImpl implements GitlabClient {
    private RestTemplate restTemplate;
    @Value("${gitlab.protocol}")
    private String gitlabProtocol;
    @Value("${gitlab.base_url}")
    private String gitlabBaseUrl;
    @Value("${gitlab.api.base_uri}")
    private String gitlabApiBaseUri;
    @Value("${gitlab.api.auth.token}")
    private String token;
    private Integer testTypeId = 1; //cypress

    @Autowired
    public GitlabClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Метод, возвращащий список каталогов, в которых лежат тесты с указанными входными параметрами в гитлаб
     * @param projectIdGitlab id проекта в GitLab
     * @param gitRepositoryRef имя ветки
     * @return ответ, содержащий список каталогов в виде объекта CatalogsList (List String)
     */
    @Override
    public CatalogsList getTestCatalogsList(long projectIdGitlab, String gitRepositoryRef) {
        UriComponents fileResourceUrl = UriComponentsBuilder.newInstance()
                .scheme(gitlabProtocol)
                .host(gitlabBaseUrl)
                .path(gitlabApiBaseUri)
                .path("projects/")
                .path(String.valueOf(projectIdGitlab))
                .path("/repository/tree")
                .query("path=cypress/e2e&per_page=1000")
                .query("ref={gitRepositoryRef}")
                .buildAndExpand(gitRepositoryRef);
        log.info(fileResourceUrl.toUriString());
        ResponseEntity<Catalog[]> responseList = restTemplate.exchange(fileResourceUrl.toUri(), HttpMethod.GET, setToken(), Catalog[].class);
        if (responseList.getStatusCode() == HttpStatus.OK) {
            if (responseList.getBody() == null || responseList.getBody().length < 1) {
                throw new DataNotFoundException("dataNotFoundException Body is empty. ResponseCode = " + responseList.getStatusCode().value());
            } else {
                return new CatalogsList(Arrays.asList(responseList.getBody()));
            }
        } else {
            throw new GitlabResponseException("HttpStatus is " + responseList.getStatusCode());
        }
    }

    /**
     * Метод, используемый для авторизации в гитлабе
     * @return возвращает HttpEntity с устанавленным токеном авторизации
     */
    private HttpEntity<String> setToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", token);
        return new HttpEntity<>(null, headers);
    }
}
