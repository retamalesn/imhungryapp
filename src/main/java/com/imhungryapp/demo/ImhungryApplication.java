package com.imhungryapp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class ImhungryApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(ImhungryApplication.class, args);
	}

}
