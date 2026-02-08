package com.baekgwa.vibecodingtodolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VibeCodingTodoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(VibeCodingTodoListApplication.class, args);
	}

}
