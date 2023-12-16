package com.application.toDoList;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ToDoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(TaskServiceImpl taskService) {
//		return args -> {
////			Task Task1 = new Task("");
//		};
//	}

}
