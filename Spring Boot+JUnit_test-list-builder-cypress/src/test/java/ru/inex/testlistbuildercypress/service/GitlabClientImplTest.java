package ru.inex.testlistbuildercypress.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.inex.testlistbuildercypress.config.AppTestConfig;
import ru.inex.testlistbuildercypress.entity.Catalog;
import ru.inex.testlistbuildercypress.exception.DataNotFoundException;
import ru.inex.testlistbuildercypress.exception.GitlabResponseException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = AppTestConfig.class)
@ActiveProfiles("test")
@Slf4j
class GitlabClientImplTest {
    private final GitlabClientImpl gitlabClient;

    @Value("${gitlab.protocol}")
    private String gitlabProtocol;
    @Value("${gitlab.base_url}")
    private String gitlabBaseUrl;
    @Value("${gitlab.api.base_uri}")
    private String gitlabApiBaseUri;
    @Value("${gitlab.api.auth.token}")
    private String token;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    public GitlabClientImplTest(GitlabClientImpl gitlabClient) {
        this.gitlabClient = gitlabClient;
    }

    @BeforeEach
    public void setup() {
        Catalog[] catalogs = new Catalog[4];
        catalogs[0] = new Catalog("cypress/e2e/_common");
        catalogs[1] = new Catalog("cypress/e2e/_noifr");
        catalogs[2] = new Catalog("cypress/e2e/_themes");
        catalogs[3] = new Catalog("cypress/e2e/account");
        ResponseEntity<Catalog[]> positive = new ResponseEntity<>(catalogs, HttpStatus.OK);
        ResponseEntity<Catalog[]> noData = new ResponseEntity<>(new Catalog[0], HttpStatus.OK);
        ResponseEntity<Catalog[]> gitLabError = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        UriComponents positiveUri = UriComponentsBuilder.newInstance()
                .scheme(gitlabProtocol)
                .host(gitlabBaseUrl)
                .path(gitlabApiBaseUri)
                .path("projects/")
                .path(String.valueOf(0))
                .path("/repository/tree")
                .query("path=cypress/e2e&per_page=1000")
                .query("ref={positive}")
                .buildAndExpand("positive");
        UriComponents noDataUri = UriComponentsBuilder.newInstance()
                .scheme(gitlabProtocol)
                .host(gitlabBaseUrl)
                .path(gitlabApiBaseUri)
                .path("projects/")
                .path(String.valueOf(0))
                .path("/repository/tree")
                .query("path=cypress/e2e&per_page=1000")
                .query("ref={noData}")
                .buildAndExpand("noData");
        UriComponents gitLabErrorUri = UriComponentsBuilder.newInstance()
                .scheme(gitlabProtocol)
                .host(gitlabBaseUrl)
                .path(gitlabApiBaseUri)
                .path("projects/")
                .path(String.valueOf(0))
                .path("/repository/tree")
                .query("path=cypress/e2e&per_page=1000")
                .query("ref={gitLabError}")
                .buildAndExpand("gitLabError");
        Mockito.doReturn(positive).when(restTemplate).exchange(positiveUri.toUri(), HttpMethod.GET, setToken(), Catalog[].class);
        Mockito.doReturn(noData).when(restTemplate).exchange(noDataUri.toUri(), HttpMethod.GET, setToken(), Catalog[].class);
        Mockito.doReturn(gitLabError).when(restTemplate).exchange(gitLabErrorUri.toUri(), HttpMethod.GET, setToken(), Catalog[].class);
    }

    @Test
    public void getTestCatalogsFromGitlabGitLabError() {
        GitlabResponseException gitlabResponseException = assertThrows(GitlabResponseException.class, () ->
                gitlabClient.getTestCatalogsList(0, "gitLabError"));
        assertNotNull(gitlabResponseException.getMessage());
    }

    @Test
    public void getTestCatalogsFromGitlabNullData() throws DataNotFoundException {
        DataNotFoundException dataNotFoundException = assertThrows(DataNotFoundException.class, () ->
                gitlabClient.getTestCatalogsList(0, "noData"));
        assertNotNull(dataNotFoundException.getMessage());
    }

    @Test
    void getTestCatalogsListFromGitlab() {
        gitlabClient.getTestCatalogsList(0, "positive");
    }

    private HttpEntity<String> setToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", token);
        return new HttpEntity<>(null, headers);
    }
}