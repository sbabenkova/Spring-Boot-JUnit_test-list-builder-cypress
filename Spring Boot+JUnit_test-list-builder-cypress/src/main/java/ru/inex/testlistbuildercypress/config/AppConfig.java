package ru.inex.testlistbuildercypress.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    /**
     * Используется в GitlabClientImpl для обращения к API Gitlab
     */
    @Bean
    public RestTemplate restTemplate(){
    return new RestTemplate();
    }
}
