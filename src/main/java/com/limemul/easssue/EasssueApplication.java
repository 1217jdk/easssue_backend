package com.limemul.easssue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.limemul.easssue.repo")
@EnableMongoRepositories(basePackages = "com.limemul.easssue.mongorepo")
public class EasssueApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasssueApplication.class, args);
	}

}
