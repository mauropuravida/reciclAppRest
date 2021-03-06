package com.example.demo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.example.controller","com.example.service"})
@EntityScan("com.example.model")
@EnableJpaRepositories("com.example.repo")
public class Demo2Application {

	public static void main(String[] args) {
		 System.getProperty("java.classpath");
		SpringApplication.run(Demo2Application.class, args);
	}

}
