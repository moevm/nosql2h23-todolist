package com.application.toDoList;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.enums.ProjectStatus;
import com.application.toDoList.enums.TaskStatus;
import com.application.toDoList.repositories.PersonRepository;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.repositories.SubtaskRepository;
import com.application.toDoList.repositories.TaskRepository;
import com.application.toDoList.services.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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
						  TaskRepository taskRepository, SubtaskRepository subtaskRepository, PersonRepository personRepository){

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

			Person NatalyPerson = personRepository.findByEmail("nataly@gmail.com").get();
			Person XeniaPerson = personRepository.findByEmail("xeniaL@gmail.com").get();
			Person KatyPerson = personRepository.findByEmail( "katy@gmail.com").get();
			Person TestPerson = personRepository.findByEmail("test@gmail.com").get();
			Person AnnPerson = personRepository.findByEmail("anna@gmail.com").get();
			Person DariaPerson = personRepository.findByEmail("daria@gmail.com").get();
			Person DanilPerson = personRepository.findByEmail("dan@gmail.com").get();

			/**
			 * Первый проект
			 **/

			Subtask SubtaskForCalc1 = new Subtask("Проведение анализа текущего состояния бизнес-процессов", LocalDateTime.now(), false);
			Subtask SubtaskForCalc2 = new Subtask("Определение цифровых технологий для внедрения", LocalDateTime.now(), true);
			Subtask SubtaskForCalc3 = new Subtask("Составление стратегии поэтапной цифровизации", LocalDateTime.now(), false);
			subtaskRepository.save(SubtaskForCalc1);
			subtaskRepository.save(SubtaskForCalc2);
			subtaskRepository.save(SubtaskForCalc3);
			ArrayList<Subtask> subtaskForCal = new ArrayList<>();
			subtaskForCal.add(SubtaskForCalc1);
			subtaskForCal.add(SubtaskForCalc2);
			subtaskForCal.add(SubtaskForCalc3);

			Subtask SubtaskForCoding1 = new Subtask("Интеграция систем электронного документооборота", LocalDateTime.now(), true);
			Subtask SubtaskForCoding2 = new Subtask("Создание цифрового центра обработки данных", LocalDateTime.now(), false);
			subtaskRepository.save(SubtaskForCoding1);
			subtaskRepository.save(SubtaskForCoding2);
			ArrayList<Subtask> subtaskForCoding = new ArrayList<>();
			subtaskForCoding.add(SubtaskForCoding1);
			subtaskForCoding.add(SubtaskForCoding2);

			Task TaskForCalc = new Task("Разработка стратегии цифровизации", LocalDateTime.now(),
					LocalDateTime.of(2023, 11, 23,11,30,46),
					TaskStatus.INCOMPLETE, KatyPerson, XeniaPerson, subtaskForCal);
			Task TaskForCoding = new Task("Интеграция цифровых платформ", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 23,11,30,46),
					TaskStatus.INCOMPLETE, KatyPerson, DariaPerson, subtaskForCoding);
			taskRepository.save(TaskForCalc);
			taskRepository.save(TaskForCoding);
			ArrayList<Task> TasksForCode = new ArrayList<>();
			TasksForCode.add(TaskForCalc);
			TasksForCode.add(TaskForCoding);

			ArrayList<Subtask> subtaskForFlight1 = new ArrayList<>();
			Task TaskForFlight1 = new Task("Оптимизация бизнес-процессов", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 23,10,30,46),
					TaskStatus.COMPLETE, KatyPerson, DariaPerson, subtaskForFlight1);
			taskRepository.save(TaskForFlight1);
			TasksForCode.add(TaskForFlight1);

			Set<Person> Executers = new HashSet<>();
			Executers.add(XeniaPerson);
			Executers.add(DariaPerson);
			Executers.add(KatyPerson);

			Project ProjectCode = new Project("Цифровизация", ProjectStatus.IN_PROGRESS, Executers,
					TasksForCode);
			projectRepository.save(ProjectCode);


			/**
			 * Второй проект
			 **/

			Subtask SubtaskForEntity1 = new Subtask("Добавить сущность задач", LocalDateTime.now(), false);
			Subtask SubtaskForEntity2 = new Subtask("Добавить сущность проектов", LocalDateTime.now(), false);
			subtaskRepository.save(SubtaskForEntity1);
			subtaskRepository.save(SubtaskForEntity2);
			ArrayList<Subtask> subtaskForBD = new ArrayList<>();
			subtaskForBD.add(SubtaskForEntity1);
			subtaskForBD.add(SubtaskForEntity2);

			ArrayList<Subtask> subtaskForControl = new ArrayList<>();
			ArrayList<Subtask> subtasksForRep = new ArrayList<>();
			ArrayList<Subtask> subtasksForServ = new ArrayList<>();
			ArrayList<Subtask> subTasksForTests = new ArrayList<>();

			Task TaskForBD = new Task("Добавить в базу данных новые сущности", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 5,18,30,00),
					TaskStatus.INCOMPLETE, DanilPerson, XeniaPerson, subtaskForBD);
			Task TaskForControl = new Task("Написать контроллеры", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 3,17,00,00),
					TaskStatus.COMPLETE, KatyPerson, XeniaPerson, subtaskForControl);
			Task TaskForRep = new Task("Обновить репозитории", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 11,11,30,46),
					TaskStatus.COMPLETE, DanilPerson, DariaPerson, subtasksForRep);
			Task TaskForServ = new Task("Написание Сервисного слоя", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 12,10,30,46),
					TaskStatus.COMPLETE, KatyPerson, NatalyPerson, subtasksForServ);
			Task taskForTests = new Task("Протестировать методы", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 12,11,30,46),
					TaskStatus.COMPLETE, KatyPerson, NatalyPerson, subTasksForTests);

			taskRepository.save(TaskForBD);
			taskRepository.save(TaskForControl);
			taskRepository.save(TaskForRep);
			taskRepository.save(TaskForServ);
			taskRepository.save(taskForTests);

			ArrayList<Task> TasksForCorp = new ArrayList<>();
			TasksForCorp.add(TaskForBD);
			TasksForCorp.add(TaskForControl);
			TasksForCorp.add(TaskForRep);
			TasksForCorp.add(TaskForServ);
			TasksForCorp.add(taskForTests);

			Set<Person> ExecutersForCorp = new HashSet<>();
			ExecutersForCorp.add(XeniaPerson);
			ExecutersForCorp.add(DariaPerson);
			ExecutersForCorp.add(KatyPerson);
			ExecutersForCorp.add(DanilPerson);
			ExecutersForCorp.add(NatalyPerson);

			Project ProjectCorp = new Project("КВПОР", ProjectStatus.IN_PROGRESS, ExecutersForCorp,
					TasksForCorp);
			projectRepository.save(ProjectCorp);


			/**
			 * Третий проект
			 **/

			Subtask SubtaskForResearch1 = new Subtask("Анализ текущих тенденций в области искусственного интеллекта", LocalDateTime.now(), false);
			Subtask SubtaskForResearch2 = new Subtask("Идентификация наиболее подходящих алгоритмов", LocalDateTime.now(), false);
			Subtask SubtaskForResearch3 = new Subtask("Оценка применимости алгоритмов", LocalDateTime.now(), false);
			subtaskRepository.save(SubtaskForResearch1);
			subtaskRepository.save(SubtaskForResearch2);
			subtaskRepository.save(SubtaskForResearch3);
			ArrayList<Subtask> subtaskForResearch = new ArrayList<>();
			subtaskForResearch.add(SubtaskForResearch1);
			subtaskForResearch.add(SubtaskForResearch2);
			subtaskForResearch.add(SubtaskForResearch3);

			Subtask SubtaskForProg1 = new Subtask("Реализация прототипов алгоритмов ", LocalDateTime.now(), false);
			Subtask SubtaskForProg2 = new Subtask("Подготовка тестовых сценариев", LocalDateTime.now(), true);
			subtaskRepository.save(SubtaskForProg1);
			subtaskRepository.save(SubtaskForProg2);
			ArrayList<Subtask> subtaskForProg = new ArrayList<>();
			subtaskForProg.add(SubtaskForProg1);
			subtaskForProg.add(SubtaskForProg2);

			Subtask SubtaskForIntegr1 = new Subtask("Проектирование архитектуры интеграции", LocalDateTime.now(), false);
			Subtask SubtaskForIntegr2 = new Subtask("Разработка API для взаимодействия с ИИ-алгоритмами", LocalDateTime.now(), false);
			Subtask SubtaskForIntegr3 = new Subtask("Интеграция алгоритмов в существующую систему", LocalDateTime.now(), true);
			subtaskRepository.save(SubtaskForIntegr1);
			subtaskRepository.save(SubtaskForIntegr2);
			subtaskRepository.save(SubtaskForIntegr3);
			ArrayList<Subtask> subtaskForIntegr = new ArrayList<>();
			subtaskForIntegr.add(SubtaskForIntegr1);
			subtaskForIntegr.add(SubtaskForIntegr2);
			subtaskForIntegr.add(SubtaskForIntegr3);

			Subtask SubtaskForTeach = new Subtask("Сбор и предобработка данных", LocalDateTime.now(), true);
			subtaskRepository.save(SubtaskForTeach);
			ArrayList<Subtask> subtasksForTeach = new ArrayList<>();
			subtasksForTeach.add(SubtaskForTeach);

			Subtask SubtaskForMonitor1 = new Subtask("Определение ключевых показателей эффективности", LocalDateTime.now(), true);
			Subtask SubtaskForMonitor2 = new Subtask("Разработка дашбордов для визуализации данных", LocalDateTime.now(), true);
			subtaskRepository.save(SubtaskForMonitor1);
			subtaskRepository.save(SubtaskForMonitor2);
			ArrayList<Subtask> subtaskForMonitor = new ArrayList<>();
			subtaskForMonitor.add(SubtaskForMonitor1);
			subtaskForMonitor.add(SubtaskForMonitor2);

			Task TaskForResearch = new Task("Исследование и выбор ИИ-алгоритмов", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 10,18,30,00),
					TaskStatus.INCOMPLETE, DanilPerson, DariaPerson, subtaskForResearch);
			Task TaskForProg = new Task("Разработка и тестирование алгоритмов оптимизации", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 10,17,00,00),
					TaskStatus.INCOMPLETE, KatyPerson, XeniaPerson, subtaskForProg);
			Task TaskForIntegr = new Task("Интеграция алгоритмов в рабочую среду", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 10,11,30,46),
					TaskStatus.INCOMPLETE, DanilPerson, DariaPerson, subtaskForIntegr);
			Task TaskForTeach = new Task("Обучение системы на основе данных", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 8,10,30,46),
					TaskStatus.COMPLETE, KatyPerson, NatalyPerson, subtasksForTeach);
			Task taskForMonitor = new Task("Оценка и мониторинг эффективности", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 8,11,30,46),
					TaskStatus.COMPLETE, KatyPerson, NatalyPerson, subtaskForMonitor);

			taskRepository.save(TaskForResearch);
			taskRepository.save(TaskForProg);
			taskRepository.save(TaskForIntegr);
			taskRepository.save(TaskForTeach);
			taskRepository.save(taskForMonitor);

			ArrayList<Task> TasksForAI = new ArrayList<>();
			TasksForAI.add(TaskForResearch);
			TasksForAI.add(TaskForProg);
			TasksForAI.add(TaskForIntegr);
			TasksForAI.add(TaskForTeach);
			TasksForAI.add(taskForMonitor);

			Set<Person> ExecutersForAI = new HashSet<>();
			ExecutersForAI.add(XeniaPerson);
			ExecutersForAI.add(DariaPerson);
			ExecutersForAI.add(KatyPerson);
			ExecutersForAI.add(DanilPerson);
			ExecutersForAI.add(NatalyPerson);

			Project ProjectAI = new Project("Внедрение ИИ", ProjectStatus.IN_PROGRESS, ExecutersForAI,
					TasksForAI);
			projectRepository.save(ProjectAI);


			/**
			 * Четвертый проект
			 **/

			Subtask SubtaskForRazrab1 = new Subtask("Исследование последних тенденций в цифровой графике", LocalDateTime.now(), false);
			Subtask SubtaskForRazrab2 = new Subtask("Создание концепций инновационных графических решений", LocalDateTime.now(), false);
			subtaskRepository.save(SubtaskForRazrab1);
			subtaskRepository.save(SubtaskForRazrab2);
			ArrayList<Subtask> subtaskForRazrab = new ArrayList<>();
			subtaskForRazrab.add(SubtaskForRazrab1);
			subtaskForRazrab.add(SubtaskForRazrab2);

			Subtask SubtaskForOptm1 = new Subtask("Определение узких мест в производительности", LocalDateTime.now(), false);
			Subtask SubtaskForOptm2 = new Subtask("Разработка стратегии оптимизации и их внедрение", LocalDateTime.now(), false);
			subtaskRepository.save(SubtaskForOptm1);
			subtaskRepository.save(SubtaskForOptm2);
			ArrayList<Subtask> subtaskForOptm = new ArrayList<>();
			subtaskForOptm.add(SubtaskForOptm1);
			subtaskForOptm.add(SubtaskForOptm2);

			Subtask SubtaskForAnalize1 = new Subtask("Изучение возможностей графического взаимодействия", LocalDateTime.now(), false);
			Subtask SubtaskForAnalize2 = new Subtask("Создание прототипов и демонстраций для VR/AR платформ", LocalDateTime.now(), false);
			subtaskRepository.save(SubtaskForAnalize1);
			subtaskRepository.save(SubtaskForAnalize2);
			ArrayList<Subtask> subtaskForAnalize = new ArrayList<>();
			subtaskForAnalize.add(SubtaskForAnalize1);
			subtaskForAnalize.add(SubtaskForAnalize2);

			Subtask SubtaskTaskForSup1 = new Subtask("Разработка интерактивных обучающих курсов", LocalDateTime.now(), true);
			Subtask SubtaskTaskForSup2 = new Subtask("Подготовка FAQ и руководств пользователя", LocalDateTime.now(), true);
			subtaskRepository.save(SubtaskTaskForSup1);
			subtaskRepository.save(SubtaskTaskForSup2);
			ArrayList<Subtask> subtaskTaskForSup = new ArrayList<>();
			subtaskTaskForSup.add(SubtaskTaskForSup1);
			subtaskTaskForSup.add(SubtaskTaskForSup2);

			Task TaskForRazrab = new Task("Разработка инновационных графических решений", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 11,18,30,00),
					TaskStatus.INCOMPLETE, DanilPerson, NatalyPerson, subtaskForRazrab);
			Task TaskForOptm = new Task("Оптимизация производительности графических приложений", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 11,17,00,00),
					TaskStatus.INCOMPLETE, KatyPerson, XeniaPerson, subtaskForOptm);
			Task TaskForAnalize = new Task("Анализ современных технологий VR/AR", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 11,11,30,46),
					TaskStatus.INCOMPLETE, DanilPerson, DariaPerson, subtaskForAnalize);
			Task TaskForSup = new Task("Обучение и поддержка пользователей", LocalDateTime.now(),
					LocalDateTime.of(2023, 12, 9,10,30,46),
					TaskStatus.COMPLETE, KatyPerson, NatalyPerson, subtaskTaskForSup);

			taskRepository.save(TaskForRazrab);
			taskRepository.save(TaskForOptm);
			taskRepository.save(TaskForAnalize);
			taskRepository.save(TaskForSup);

			ArrayList<Task> TasksForGraph = new ArrayList<>();
			TasksForGraph.add(TaskForRazrab);
			TasksForGraph.add(TaskForOptm);
			TasksForGraph.add(TaskForAnalize);
			TasksForGraph.add(TaskForSup);

			Set<Person> ExecutersForGraph = new HashSet<>();
			ExecutersForGraph.add(XeniaPerson);
			ExecutersForGraph.add(DariaPerson);
			ExecutersForGraph.add(KatyPerson);
			ExecutersForGraph.add(DanilPerson);
			ExecutersForGraph.add(NatalyPerson);

			Project ProjectGraph = new Project("Графикс", ProjectStatus.IN_PROGRESS, ExecutersForGraph,
					TasksForGraph);
			projectRepository.save(ProjectGraph);
		};
	}

}
