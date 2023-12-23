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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.application.toDoList.enums.ProjectStatus.IN_PROGRESS;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final PersonService personService;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, PersonService personService, PersonRepository personRepository, TaskRepository taskRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.personService = personService;
        this.personRepository = personRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public Project create(ProjectDTO projectDTO) {
        this.nameException(projectDTO.getName());
        Project project = convertToProject(projectDTO);
        project.setStatus(IN_PROGRESS);
        project.getExecutors().add(personService.getUser());
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
        if (project.getExecutors().contains(personService.getUser())){
            if (projectRepository.findByName(projectDTO.getName()).isEmpty() ||
                    Objects.equals(projectRepository.findByName(projectDTO.getName()).get().getId(), project_id)){
                project.setName(projectDTO.getName());
                if (projectDTO.getStatus() != null)
                    project.setStatus(Enum.valueOf(ProjectStatus.class, projectDTO.getStatus()));
                return projectRepository.save(project);
            }
            throw new ProjectNameException();
        }
        throw new PersonNotInProjectException();
    }

    public void deleteTask(String task_id, String project_id) {
        if (taskRepository.findById(task_id).isEmpty())
            throw new TaskNotFoundException();

        if(projectRepository.findById(project_id).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(project_id).get();
        Task task = taskRepository.findById(task_id).get();

        project.getTasks().remove(task);
        taskRepository.delete(task);
        projectRepository.save(project);
    }

    public List<Task> findAllTasks(String project_id) {
        if(projectRepository.findById(project_id).isEmpty())
            throw new ProjectNotFoundException();

        return projectRepository.findById(project_id).get().getTasks();
    }

    public Person addPerson(String project_id, String person_id) {
        Project project = this.findById(project_id);
        if (personRepository.findById(person_id).isPresent()) {
            Person person = personRepository.findById(person_id).get();
            project.getExecutors().add(person);

            projectRepository.save(project);
            return person;
        }
        throw new PersonNotFoundException();
    }

    public void deletePerson(String project_id, String person_id) {
        Project project = this.findById(project_id);
        if (personRepository.findById(person_id).isPresent()) {
            Person person = personRepository.findById(person_id).get();
            project.getExecutors().remove(person);
            projectRepository.save(project);
        }
        throw new PersonNotFoundException();
    }
    public Project findCheck(Optional<Project> foundProject) {
        Person person = personService.getUser();
        if (foundProject.isPresent()){
            if (foundProject.get().getExecutors().contains(person)
                    || Objects.equals(person.getRole(), "ROLE_ADMIN")){
                return foundProject.get();
            }
            else throw new PersonNotInProjectException();
        }
        throw new ProjectNotFoundException();
    }
    public Project findById(String id) {
        Optional<Project> foundProject = projectRepository.findById(id);
        return this.findCheck(foundProject);
    }

    public Project findByName(String name) {
        Optional<Project> foundProject = projectRepository.findByName(name);
        return this.findCheck(foundProject);

    }

    public void nameException(String name) {
        Optional<Project> foundProject = projectRepository.findByName(name);
        foundProject.ifPresent(value -> {
            throw new ProjectNameException();
        });
    }

    public ProjectDTO convertToProjectDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }
    public Project convertToProject(ProjectDTO projectDTO) {
        Project project = modelMapper.map(projectDTO, Project.class);
        project.setExecutors(new HashSet<>());
        project.setTasks(new ArrayList<>());
        return project;
    }


}
