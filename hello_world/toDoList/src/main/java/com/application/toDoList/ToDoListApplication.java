package com.application.toDoList;


import com.application.toDoList.domains.Person;
import com.application.toDoList.services.ProjectService;
import com.application.toDoList.services.RegistrationService;
import com.application.toDoList.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ToDoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	CommandLineRunner run(RegistrationService registrationService, TaskService taskService, ProjectService projectService){

		return args -> {
			Person Nataly = new Person("Наталья", "Евгенина", "nataly@gmail.com", "1234");
			Person Xenia = new Person("Ксения", "Львова", "xeniaL@gmail.com", "0456");
			Person Katy = new Person("Екатерина", "Смирнова", "katy@gmail.com", "77711");
			Person Test = new Person("Тестовый", "Пользователь", "test@gmail.com", "Test");
			Person Ann = new Person("Анна", "Ахматова", "anna@gmail.com", "0192");
			Person Daria = new Person("Дарья", "Потомова", "daria@gmail.com", "82846");
			Person Danil = new Person("Данил", "Комиссаров", "dan@gmail.com", "qwerty");
			Katy.setRole("ROLE_ADMIN");
			Danil.setRole("ROLE_ADMIN");
			registrationService.register(Nataly);
			registrationService.register(Xenia);
			registrationService.register(Katy);
			registrationService.register(Test);
			registrationService.register(Ann);
			registrationService.register(Danil);
			registrationService.register(Daria);


		};
	}

}
