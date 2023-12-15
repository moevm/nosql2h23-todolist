package com.application.toDoList.services;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.ProjectDTO;
import com.application.toDoList.enums.ProjectStatus;
import com.application.toDoList.repositories.PersonRepository;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.repositories.TaskRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository, PersonRepository personRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
    }

    public Project create(ProjectDTO projectDTO) {
        this.findByName(projectDTO.getName());
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setStatus(Enum.valueOf(ProjectStatus.class, projectDTO.getStatus()));
        return projectRepository.save(project);
    }

    public Project change(ProjectDTO projectDTO, ObjectId project_id) {
        Project project = this.findById(project_id);
        this.findByName(projectDTO.getName());
        project.setName(projectDTO.getName());
        project.setStatus(Enum.valueOf(ProjectStatus.class, projectDTO.getStatus()));
        return projectRepository.save(project);
    }

    public Task addTask(ObjectId project_id, ObjectId task_id) {
        Project project = this.findById(project_id);
        Task task = new Task();
        if (taskRepository.findById(task_id).isPresent()) {
            task = taskRepository.findById(task_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ЗАДАЧИ НЕ СУЩЕСТВУЕТ
        }
        project.getTasks().add(task);
        task.setProject(project);
        return task;
    }

    public void deleteTask(ObjectId project_id, ObjectId task_id) {
        Project project = this.findById(project_id);
        Task task = new Task();
        if (taskRepository.findById(task_id).isPresent()) {
            task = taskRepository.findById(task_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ЗАДАЧИ НЕ СУЩЕСТВУЕТ
        }
        project.getTasks().remove(task);
    }

    public Person addPerson(ObjectId project_id, ObjectId person_id) {
        Project project = this.findById(project_id);
        Person person = new Person();
        if (personRepository.findById(person_id).isPresent()) {
            person = personRepository.findById(person_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ЧЕЛОВЕКА НЕ СУЩЕСТВУЕТ
        }
        project.getExecutors().add(person);
        person.getProjects().add(project);
        return person;
    }

    public void deletePerson(ObjectId project_id, ObjectId person_id) {
        Project project = this.findById(project_id);
        Person person = new Person();
        if (personRepository.findById(person_id).isPresent()) {
            person = personRepository.findById(person_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ЗАДАЧИ НЕ СУЩЕСТВУЕТ
        }
        project.getExecutors().remove(person);
        person.getProjects().remove(project);
    }
    public Project findById(ObjectId id) {
        Optional<Project> foundProject = projectRepository.findById(id);
        return foundProject.orElseThrow(RuntimeException::new);
        // ИСКЛЮЧЕНИЕ ПРОЕКТА НЕ СУЩЕСТВУЕТ
    }

    public void findByName(String name) {
        Optional<Project> foundProject = projectRepository.findByName(name);
        foundProject.ifPresent(value -> {
            throw new RuntimeException();
        });
        // ИСКЛЮЧЕНИЕ ИМЯ ПРОЕКТА ДОЛЖНО БЫТЬ УНИКАЛЬНЫМ
    }
}
