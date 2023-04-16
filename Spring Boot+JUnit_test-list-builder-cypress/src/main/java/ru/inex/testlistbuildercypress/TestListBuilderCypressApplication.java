package ru.inex.testlistbuildercypress;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@Slf4j
public class TestListBuilderCypressApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestListBuilderCypressApplication.class, args);
		log.info("TestListBuilderCypressApplication started");
	}

}
