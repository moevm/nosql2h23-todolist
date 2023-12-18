package com.application.toDoList.services;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.ProjectDTO;
import com.application.toDoList.enums.ProjectStatus;
import com.application.toDoList.exceptions.*;
import com.application.toDoList.repositories.PersonRepository;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.repositories.TaskRepository;
import com.application.toDoList.security.PersonDetails;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final PersonService personService;
    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, PersonService personService, PersonRepository personRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.personService = personService;
        this.personRepository = personRepository;
        this.taskRepository = taskRepository;
    }

    public Project create(ProjectDTO projectDTO) {
        this.nameException(projectDTO.getName());
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setStatus(Enum.valueOf(ProjectStatus.class, projectDTO.getStatus()));
        project.setExecutors(new HashSet<>());
        project.setTasks(new ArrayList<>());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        project.getExecutors().add(personService.findEmail(personDetails.getUsername()));

        return projectRepository.save(project);
    }
    public void delete(String project_id) {
        projectRepository.deleteById(project_id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public List<Project> findAllByStatus(String status) {
        return projectRepository.findAllByStatus(status);
    }

    public List<Project> findAllForPerson(String person_id){
        if (personRepository.findById(person_id).isPresent()) {
            List<Project> allProjectsForPerson = new ArrayList<>();
            for (Project project : this.findAll()){
                if (project.getExecutors() != null && project.getExecutors().contains(personRepository.findById(person_id).get())){
                    allProjectsForPerson.add(project);
                }
            }
            return allProjectsForPerson;
        }
        throw new PersonNotFoundException();
    }

    public Project change(ProjectDTO projectDTO, String project_id) {
        Project project = this.findById(project_id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (project.getExecutors().contains(personService.findEmail(personDetails.getUsername()))){
            if (projectRepository.findByName(projectDTO.getName()).isEmpty() ||
                    Objects.equals(projectRepository.findByName(projectDTO.getName()).get().getId(), project_id)){
                project.setName(projectDTO.getName());
                project.setStatus(Enum.valueOf(ProjectStatus.class, projectDTO.getStatus()));
                return projectRepository.save(project);
            }
            throw new ProjectNameException();
        }
        throw new PersonNotInProjectException();
    }

    public Task addTask(String task_id, String project_id) {
        if (taskRepository.findById(task_id).isEmpty())
            throw new TaskNotFoundException();

        if(projectRepository.findById(project_id).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(project_id).get();
        Task task = taskRepository.findById(task_id).get();

        project.getTasks().add(task);
        projectRepository.save(project);

        return task;
    }

    public void deleteTask(String task_id, String project_id) {
        if (taskRepository.findById(task_id).isEmpty())
            throw new TaskNotFoundException();

        if(projectRepository.findById(project_id).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(project_id).get();
        Task task = taskRepository.findById(task_id).get();

        project.getTasks().remove(task);
        projectRepository.save(project);
    }

    public List<Task> findAllTasks(String project_id) {
        if(projectRepository.findById(project_id).isEmpty())
            throw new ProjectNotFoundException();

        return projectRepository.findById(project_id).get().getTasks();
    }

//    public List<Task> findAllIncompleteTasks(String project_id) {
//        if(projectRepository.findById(project_id).isEmpty())
//            throw new ProjectNotFoundException();
//
//        Project project = projectRepository.findById(project_id).get();
//
//        Bson filter = Filters.eq("")
//
//        return .getTasks();
//    }

    public Person addPerson(String project_id, String person_id) {
        Project project = this.findById(project_id);
        if (personRepository.findById(person_id).isPresent()) {
            Person person = personRepository.findById(person_id).get();
            project.getExecutors().add(person);
            return person;
        }
        throw new PersonNotFoundException();
    }

    public void deletePerson(String project_id, String person_id) {
        Project project = this.findById(project_id);
        if (personRepository.findById(person_id).isPresent()) {
            Person person = personRepository.findById(person_id).get();
            project.getExecutors().remove(person);
        }
        throw new PersonNotFoundException();
    }
    public Project findById(String id) {
        Optional<Project> foundProject = projectRepository.findById(id);
        return foundProject.orElseThrow(ProjectNotFoundException::new);
    }

    public void updateProject(String projectId) {
        if(projectRepository.findById(projectId).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(projectId).get();

        projectRepository.save(project);
    }

    public void nameException(String name) {
        Optional<Project> foundProject = projectRepository.findByName(name);
        foundProject.ifPresent(value -> {
            throw new ProjectNameException();
        });
    }
}
