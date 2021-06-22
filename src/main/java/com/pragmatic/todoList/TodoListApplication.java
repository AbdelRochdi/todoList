package com.pragmatic.todoList;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class TodoListApplication {
	
    private static final Logger logger = LoggerFactory.getLogger(TodoListApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}

	@Scheduled(fixedDelayString = "PT5S")
	void repititiveTask() throws InterruptedException {
		logger.info("Now is "+ new Date());
		Thread.sleep(1000L);
	}
	@Scheduled(fixedDelayString = "PT5S")
	void repititiveTask2() throws InterruptedException {
		logger.info("Now is2 "+ new Date());
		Thread.sleep(1000L);
	}
	
}


@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {
	
}
