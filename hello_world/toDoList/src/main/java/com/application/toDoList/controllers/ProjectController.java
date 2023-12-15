package com.application.toDoList.controllers;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.ProjectDTO;
import com.application.toDoList.enums.ProjectStatus;
import com.application.toDoList.repositories.PersonRepository;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.repositories.TaskRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;

    public ProjectController(ProjectRepository projectRepository, TaskRepository taskRepository, PersonRepository personRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
    }

    @PostMapping("/admin/create")
    public Project createProject(@RequestBody @Valid ProjectDTO projectDTO) {

        if (projectRepository.findByName(projectDTO.getName()).isPresent()) {
            // ИСКЛЮЧЕНИЕ ЧТО УЖЕ СУЩЕСТВУЕТ
            return new Project();
        }

        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setStatus(Enum.valueOf(ProjectStatus.class, projectDTO.getStatus()));
        // postService.save(convertToPost(postDTO, 0));
        return projectRepository.save(project);
    }

    @PatchMapping("/admin/{project_id}")
    public Project changeProject(@RequestBody @Valid ProjectDTO projectDTO,
                                 @PathVariable("project_id") ObjectId project_id) {
        Project project = new Project();
        if (projectRepository.findById(project_id).isPresent()){
            project = projectRepository.findById(project_id).get();
        }
        else {
            // исключение проект не найден
        }
        if (projectRepository.findByName(projectDTO.getName()).isEmpty()){
            project.setName(projectDTO.getName());
        }
        else{
            // исключение проект с таким названием уже есть
        }
        project.setStatus(Enum.valueOf(ProjectStatus.class, projectDTO.getStatus()));
        return projectRepository.save(project);
    }

    @PostMapping("/admin/{project_id}/task/{task_id}")
    public Task taskToProject(@PathVariable("project_id") ObjectId project_id,
                                 @PathVariable("task_id") ObjectId task_id) {
        Project project = new Project();
        if (projectRepository.findById(project_id).isPresent()) {
            project = projectRepository.findById(project_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ПРОЕКТА НЕ СУЩЕСТВУЕТ
        }
        Task task = new Task();
        if (taskRepository.findById(task_id).isPresent()) {
            task = taskRepository.findById(task_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ЗАДАЧИ НЕ СУЩЕСТВУЕТ
        }
        project.getTasks().add(task);
        task.setProject(project);

        return task; //taskDTO
    }

    @DeleteMapping("/admin/{project_id}/task/{task_id}")
    public ResponseEntity<HttpStatus> taskOutProject(@PathVariable("project_id") ObjectId project_id,
                                                     @PathVariable("task_id") ObjectId task_id) {
        Project project = new Project();
        if (projectRepository.findById(project_id).isPresent()) {
            project = projectRepository.findById(project_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ПРОЕКТА НЕ СУЩЕСТВУЕТ
        }
        Task task = new Task();
        if (taskRepository.findById(task_id).isPresent()) {
            task = taskRepository.findById(task_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ЗАДАЧИ НЕ СУЩЕСТВУЕТ
        }
        project.getTasks().remove(task);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/admin/{project_id}/person/{person_id}")
    public Person personToProject(@PathVariable("project_id") ObjectId project_id,
                                @PathVariable("person_id") ObjectId person_id) {
        Project project = new Project();
        if (projectRepository.findById(project_id).isPresent()) {
            project = projectRepository.findById(project_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ПРОЕКТА НЕ СУЩЕСТВУЕТ
        }
        Person person = new Person();
        if (personRepository.findById(person_id).isPresent()) {
            person = personRepository.findById(person_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ЗАДАЧИ НЕ СУЩЕСТВУЕТ
        }
        project.getExecutors().add(person);
        person.getProjects().add(project);

        return person;
    }

    @DeleteMapping("/admin/{project_id}/person/{person_id}")
    public ResponseEntity<HttpStatus> PersonOutProject(@PathVariable("project_id") ObjectId project_id,
                                                       @PathVariable("person_id") ObjectId person_id) {
        Project project = new Project();
        if (projectRepository.findById(project_id).isPresent()) {
            project = projectRepository.findById(project_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ПРОЕКТА НЕ СУЩЕСТВУЕТ
        }
        Person person = new Person();
        if (personRepository.findById(person_id).isPresent()) {
            person = personRepository.findById(person_id).get();
        }
        else {
            // ИСКЛЮЧЕНИЕ ЗАДАЧИ НЕ СУЩЕСТВУЕТ
        }
        project.getExecutors().remove(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/find")
    public Project projectByName(String projectName) {
        if (projectRepository.findByName(projectName).isPresent()){
            return projectRepository.findByName(projectName).get();
        }
        else {
            throw new RuntimeException(); //ИСКЛЮЧЕНИЕ НЕ НАЙДЕН ПРОЕКТ
        }
    }

    public ProjectDTO convertToProjectDTO(Project project) {
        return new ProjectDTO(project.getId().toHexString(), project.getName(), project.getStatus().name());
    }

    public Project convertToProject(ProjectDTO projectDTO) {
        return new Project(new ObjectId(projectDTO.getId()), projectDTO.getName(), ProjectStatus.valueOf(projectDTO.getStatus()));
    }


    //Разделить интерфейс
    //Исключения
}
