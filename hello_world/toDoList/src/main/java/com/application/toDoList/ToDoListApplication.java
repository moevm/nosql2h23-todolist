package com.application.toDoList;


import com.application.toDoList.domains.Person;
import com.application.toDoList.services.DatabaseLoaderService;
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
	CommandLineRunner run(RegistrationService registrationService, TaskService taskService, ProjectService projectService,
						  DatabaseLoaderService databaseLoaderService){

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

			/*
			String json = "[\n" +
					"    {\n" +
					"        \"id\": \"6586034502bf81310b2db2fe\",\n" +
					"        \"name\": \"Эконом\",\n" +
					"        \"status\": \"IN_PROGRESS\",\n" +
					"        \"executors\": [\n" +
					"            {\n" +
					"                \"id\": null,\n" +
					"                \"name\": \"Екатерина\",\n" +
					"                \"surname\": \"Смирнова\",\n" +
					"                \"email\": \"katy@gmail.com\",\n" +
					"                \"password\": \"$2a$10$cggRyKQw3B5LhavYbyjuJuO7Tr1oczFseBF301g6L5cka29Fm4rq6\",\n" +
					"                \"role\": \"ROLE_ADMIN\"\n" +
					"            },\n" +
					"            {\n" +
					"                \"id\": null,\n" +
					"                \"name\": \"Дарья\",\n" +
					"                \"surname\": \"Потомова\",\n" +
					"                \"email\": \"daria@gmail.com\",\n" +
					"                \"password\": \"$2a$10$bWOkf9LtR3Bm43EfXbDveOnIjqhpK0Obxf5/KU6Bx3mw5mCPbndeK\",\n" +
					"                \"role\": \"ROLE_USER\"\n" +
					"            },\n" +
					"            {\n" +
					"                \"id\": null,\n" +
					"                \"name\": \"Ксения\",\n" +
					"                \"surname\": \"Львова\",\n" +
					"                \"email\": \"xeniaL@gmail.com\",\n" +
					"                \"password\": \"$2a$10$eal.kDdWh.QJwp8uFSSsCOWXUoHHaBd2wdj/OARcGZTLJDyuu8GU2\",\n" +
					"                \"role\": \"ROLE_USER\"\n" +
					"            }\n" +
					"        ],\n" +
					"        \"tasks\": [\n" +
					"            {\n" +
					"                \"id\": \"6586034502bf81310b2db2fc\",\n" +
					"                \"title\": \"Подсчитать затраты\",\n" +
					"                \"dateOfCreation\": \"23.12.2023 00:44:37\",\n" +
					"                \"dateOfDeadline\": \"23.11.2023 11:30:46\",\n" +
					"                \"status\": \"INCOMPLETE\",\n" +
					"                \"creator\": {\n" +
					"                    \"id\": null,\n" +
					"                    \"name\": \"Екатерина\",\n" +
					"                    \"surname\": \"Смирнова\",\n" +
					"                    \"email\": \"katy@gmail.com\",\n" +
					"                    \"password\": \"$2a$10$cggRyKQw3B5LhavYbyjuJuO7Tr1oczFseBF301g6L5cka29Fm4rq6\",\n" +
					"                    \"role\": \"ROLE_ADMIN\"\n" +
					"                },\n" +
					"                \"executer\": {\n" +
					"                    \"id\": null,\n" +
					"                    \"name\": \"Ксения\",\n" +
					"                    \"surname\": \"Львова\",\n" +
					"                    \"email\": \"xeniaL@gmail.com\",\n" +
					"                    \"password\": \"$2a$10$eal.kDdWh.QJwp8uFSSsCOWXUoHHaBd2wdj/OARcGZTLJDyuu8GU2\",\n" +
					"                    \"role\": \"ROLE_USER\"\n" +
					"                },\n" +
					"                \"subtasks\": [\n" +
					"                    {\n" +
					"                        \"id\": \"6586034502bf81310b2db2f7\",\n" +
					"                        \"title\": \"Подсчет бумажек\",\n" +
					"                        \"dateOfCreation\": \"23.12.2023 00:44:37\",\n" +
					"                        \"status\": false\n" +
					"                    },\n" +
					"                    {\n" +
					"                        \"id\": \"6586034502bf81310b2db2f8\",\n" +
					"                        \"title\": \"Подсчет расходов\",\n" +
					"                        \"dateOfCreation\": \"23.12.2023 00:44:37\",\n" +
					"                        \"status\": true\n" +
					"                    },\n" +
					"                    {\n" +
					"                        \"id\": \"6586034502bf81310b2db2f9\",\n" +
					"                        \"title\": \"Подписать документы\",\n" +
					"                        \"dateOfCreation\": \"23.12.2023 00:44:37\",\n" +
					"                        \"status\": false\n" +
					"                    }\n" +
					"                ]\n" +
					"            },\n" +
					"            {\n" +
					"                \"id\": \"6586034502bf81310b2db2fd\",\n" +
					"                \"title\": \"Подключить скрипт\",\n" +
					"                \"dateOfCreation\": \"23.12.2023 00:44:37\",\n" +
					"                \"dateOfDeadline\": \"23.12.2023 11:30:46\",\n" +
					"                \"status\": \"INCOMPLETE\",\n" +
					"                \"creator\": {\n" +
					"                    \"id\": null,\n" +
					"                    \"name\": \"Екатерина\",\n" +
					"                    \"surname\": \"Смирнова\",\n" +
					"                    \"email\": \"katy@gmail.com\",\n" +
					"                    \"password\": \"$2a$10$cggRyKQw3B5LhavYbyjuJuO7Tr1oczFseBF301g6L5cka29Fm4rq6\",\n" +
					"                    \"role\": \"ROLE_ADMIN\"\n" +
					"                },\n" +
					"                \"executer\": {\n" +
					"                    \"id\": null,\n" +
					"                    \"name\": \"Дарья\",\n" +
					"                    \"surname\": \"Потомова\",\n" +
					"                    \"email\": \"daria@gmail.com\",\n" +
					"                    \"password\": \"$2a$10$bWOkf9LtR3Bm43EfXbDveOnIjqhpK0Obxf5/KU6Bx3mw5mCPbndeK\",\n" +
					"                    \"role\": \"ROLE_USER\"\n" +
					"                },\n" +
					"                \"subtasks\": [\n" +
					"                    {\n" +
					"                        \"id\": \"6586034502bf81310b2db2fa\",\n" +
					"                        \"title\": \"Инициализировать проект\",\n" +
					"                        \"dateOfCreation\": \"23.12.2023 00:44:37\",\n" +
					"                        \"status\": true\n" +
					"                    },\n" +
					"                    {\n" +
					"                        \"id\": \"6586034502bf81310b2db2fb\",\n" +
					"                        \"title\": \"Настроить сервер\",\n" +
					"                        \"dateOfCreation\": \"23.12.2023 00:44:37\",\n" +
					"                        \"status\": false\n" +
					"                    }\n" +
					"                ]\n" +
					"            }\n" +
					"        ]\n" +
					"    }\n" +
					"]\n";
			byte[] bytes = json.getBytes();

			InputStream inputStream = new ByteArrayInputStream(bytes);
			databaseLoaderService.loadDataFromFile(inputStream);
		*/
		};

	}
}
