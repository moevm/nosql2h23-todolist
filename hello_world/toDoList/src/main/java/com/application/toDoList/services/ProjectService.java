package com.application.toDoList.services;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.ProjectDTO;
import com.application.toDoList.enums.ProjectStatus;
import com.application.toDoList.exceptions.PersonNotFoundException;
import com.application.toDoList.exceptions.ProjectNameException;
import com.application.toDoList.exceptions.ProjectNotFoundException;
import com.application.toDoList.exceptions.TaskNotFoundException;
import com.application.toDoList.repositories.PersonRepository;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        this.nameException(projectDTO.getName());
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setStatus(Enum.valueOf(ProjectStatus.class, projectDTO.getStatus()));
        project.setTasks(new ArrayList<>());
        return projectRepository.save(project);
    }
    public void delete(String project_id) {
        projectRepository.deleteById(project_id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public List<Project> findAllForPerson(String person_id){
        if (personRepository.findById(person_id).isPresent()) {
            List<Project> allProjectsForPerson = new ArrayList<>();
            for (Project project : this.findAll()){
                if (project.getExecutors().contains(personRepository.findById(person_id).get())){
                    allProjectsForPerson.add(project);
                }
            }
            return allProjectsForPerson;
        }
        throw new PersonNotFoundException();
    }

    public Project change(ProjectDTO projectDTO, String project_id) {
        Project project = this.findById(project_id);
        if (projectRepository.findByName(projectDTO.getName()).isEmpty() ||
                Objects.equals(projectRepository.findByName(projectDTO.getName()).get().getId(), project_id)){
            project.setName(projectDTO.getName());
            project.setStatus(Enum.valueOf(ProjectStatus.class, projectDTO.getStatus()));
            return projectRepository.save(project);
        }
        throw new ProjectNameException();
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

    public Person addPerson(String project_id, String person_id) {
        Project project = this.findById(project_id);
        if (personRepository.findById(person_id).isPresent()) {
            Person person = personRepository.findById(person_id).get();
            project.getExecutors().add(person);
//            person.getProjects().add(project);
            return person;
        }
        throw new PersonNotFoundException();
    }

    public void deletePerson(String project_id, String person_id) {
        Project project = this.findById(project_id);
        if (personRepository.findById(person_id).isPresent()) {
            Person person = personRepository.findById(person_id).get();
            project.getExecutors().remove(person);
//            person.getProjects().remove(project);
        }
        throw new PersonNotFoundException();
    }
    public Project findById(String id) {
        Optional<Project> foundProject = projectRepository.findById(id);
        return foundProject.orElseThrow(ProjectNotFoundException::new);
    }

    public void nameException(String name) {
        Optional<Project> foundProject = projectRepository.findByName(name);
        foundProject.ifPresent(value -> {
            throw new ProjectNameException();
        });
    }
}
