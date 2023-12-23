package com.application.toDoList;


import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.enums.ProjectStatus;
import com.application.toDoList.enums.TaskStatus;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.repositories.SubtaskRepository;
import com.application.toDoList.repositories.TaskRepository;
import com.application.toDoList.services.ProjectService;
import com.application.toDoList.services.RegistrationService;
import com.application.toDoList.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	CommandLineRunner run(RegistrationService registrationService, ProjectRepository projectRepository,
						  TaskRepository taskRepository, SubtaskRepository subtaskRepository){

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

			Subtask SubtaskForCalc1 = new Subtask("Подсчет бумажек", LocalDateTime.now(), false);
			Subtask SubtaskForCalc2 = new Subtask("Подсчет расходов", LocalDateTime.now(), true);
			Subtask SubtaskForCalc3 = new Subtask("Подписать документы", LocalDateTime.now(), false);
//			subtaskRepository.save(SubtaskForCalc1);
//			subtaskRepository.save(SubtaskForCalc2);
//			subtaskRepository.save(SubtaskForCalc3);
			ArrayList<Subtask> subtaskForCal = new ArrayList<>();
			subtaskForCal.add(SubtaskForCalc1);
			subtaskForCal.add(SubtaskForCalc2);
			subtaskForCal.add(SubtaskForCalc3);

			Subtask SubtaskForCoding1 = new Subtask("Инициализировать проект", LocalDateTime.now(), true);
			Subtask SubtaskForCoding2 = new Subtask("Настроить сервер", LocalDateTime.now(), false);
//			subtaskRepository.save(SubtaskForCoding1);
//			subtaskRepository.save(SubtaskForCoding2);
			ArrayList<Subtask> subtaskForCoding = new ArrayList<>();
			subtaskForCoding.add(SubtaskForCoding1);
			subtaskForCoding.add(SubtaskForCoding2);


//			Subtask SubtaskForFlight1 = new Subtask("Написать метод подсчетов", LocalDateTime.now(), false);
//			Subtask SubtaskForFlight2 = new Subtask("Протестировать метод", LocalDateTime.now(), false);
//
//			Subtask SubtaskForFlight3 = new Subtask("Сделать интерактив карты", LocalDateTime.now(), false);
//			Subtask SubtaskForFlight4 = new Subtask("Обеспечить подключение к БД", LocalDateTime.now(), false);

			Task TaskForCalc = new Task("Подсчитать затраты", LocalDateTime.now(),
					LocalDateTime.of(2023, 11, 23,11,30,46),
					TaskStatus.INCOMPLETE, Katy, Xenia, subtaskForCal);
			Task TaskForCoding = new Task("Подключить скрипт", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 23,11,30,46),
					TaskStatus.INCOMPLETE, Katy, Daria, subtaskForCoding);
//			taskRepository.save(TaskForCalc);
//			taskRepository.save(TaskForCoding);
			ArrayList<Task> TasksForCode = new ArrayList<>();
			TasksForCode.add(TaskForCalc);
			TasksForCode.add(TaskForCoding);

			Set<Person> Executers = new HashSet<>();
			Executers.add(Xenia);
			Executers.add(Daria);
			Executers.add(Katy);

			Project ProjectCode = new Project("Эконом", ProjectStatus.IN_PROGRESS, Executers,
					TasksForCode);
//			projectRepository.save(ProjectCode);
		};
	}

}
