package com.application.toDoList.controllers;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.dto.ProjectDTO;
import com.application.toDoList.exceptions.ProjectNotFoundException;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.security.PersonDetails;
import com.application.toDoList.services.PersonService;
import com.application.toDoList.services.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, ProjectService projectService, PersonService personService, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
        this.personService = personService;
    }

    @GetMapping("/all")
    public List<Project> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (Objects.equals(personService.findEmail(personDetails.getUsername()).getRole(), "ROLE_ADMIN")){
            return projectService.findAll();
        }
        return projectService.findAllForPerson(personService.findEmail(personDetails.getUsername()).getId());
    }

    @PostMapping("/find/{person_id}")
    public List<Project> findAllForPerson(@PathVariable("person_id") String person_id) {
        return projectService.findAllForPerson(person_id);
    }

    @PostMapping("/create")
    public Project createProject(@RequestBody @Valid ProjectDTO projectDTO,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Incorrect project argument.");
        }
        return projectService.create(projectDTO);
    }

    @DeleteMapping("/delete/{project_id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("project_id") String project_id) {
        projectService.delete(project_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{project_id}")
    public Project changeProject(@RequestBody @Valid ProjectDTO projectDTO,
                                 @PathVariable("project_id") String project_id,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Incorrect project argument.");
        }
        return projectService.change(projectDTO, project_id);
    }

    /**
     *
     * Методы для Tasks
     */

    @GetMapping("/project_id}")
    public List<Task> getAllTasks(@PathVariable("project_id") String project_id) {
        return projectService.findAllTasks(project_id);
    }

    @GetMapping("/{project_id}/incomplete")
    public List<Task> getAllIncompleteTasks(@PathVariable("project_id") String project_id) {
        return projectService.findAllTasks(project_id);
    }

    @PostMapping("/{project_id}")
    public Task addTaskToProject(@RequestBody TaskToSave taskToSave,
                                 @PathVariable("project_id") String project_id) {
        Task newTask = taskService.create(taskToSave);

        return projectService.addTask(newTask.getId(), project_id);
    }

    @DeleteMapping("/{project_id}/{task_id}")
    public ResponseEntity<HttpStatus> deleteTaskFromProject(@PathVariable("project_id") String project_id,
                                                            @PathVariable("task_id") String task_id) {
        projectService.deleteTask(task_id, project_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping ("/{project_id}/{task_id}")
    public Task updateTask(@PathVariable("project_id") String project_id,
                           @PathVariable("task_id") String task_id,
                           @RequestBody TaskToUpdate taskToUpdate) {
        Task existingTask = taskService.updateTask(task_id, taskToUpdate);

        projectService.updateProject(project_id);
        return existingTask;
    }

    /**
     *
     * Методы для subtask
     */
    @GetMapping("/{project_id}/{task_id}/subtasks")
    public List<Subtask> getAllSubtasksOfTask(@PathVariable("project_id") String project_id,
                                                   @PathVariable("task_id") String task_id)         {
        return taskService.findAllSubtasks(task_id);
    }

    @PostMapping("/{project_id}/{task_id}/add-subtask")
    public ResponseEntity<?> addNewSubtaskToTask(@PathVariable("project_id") String project_id,
                                                 @PathVariable("task_id") String task_id,
                                                 @RequestBody SubtaskToSave subtaskToSave) {
        Subtask subtask = subtaskService.create(subtaskToSave);
        Task task = taskService.addSubtaskToTask(task_id, subtask);
        projectService.updateProject(project_id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PatchMapping("/{project_id}/{task_id}/{subtask_id}")
    public ResponseEntity<?> updateSubtask(@PathVariable("project_id") String project_id,
                                                 @PathVariable("task_id") String task_id,
                                                 @PathVariable("subtask_id") String subtask_id,
                                                 @RequestBody SubtaskToUpdate subtaskToUpdate) {
        Subtask subtask = subtaskService.updateSubtask(subtask_id, subtaskToUpdate);

        taskService.uodateTaskInDB(task_id);
        projectService.updateProject(project_id);

        return new ResponseEntity<>(subtask, HttpStatus.OK);
    }

    @DeleteMapping("/{project_id}/{task_id}/{subtask_id}")
    public ResponseEntity<?> updateSubtask(@PathVariable("project_id") String project_id,
                                           @PathVariable("task_id") String task_id,
                                           @PathVariable("subtask_id") String subtask_id) {

        taskService.deleteSubtask(task_id, subtask_id);
        projectService.updateProject(project_id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     *
     * Методы для Person
     */


    @PostMapping("/{project_id}/person/{person_id}")
    public Person personToProject(@PathVariable("project_id") String project_id,
                                @PathVariable("person_id") String person_id) {
        return projectService.addPerson(project_id, person_id);
    }

    @DeleteMapping("/{project_id}/person/{person_id}")
    public ResponseEntity<HttpStatus> PersonOutProject(@PathVariable("project_id") String project_id,
                                                       @PathVariable("person_id") String person_id) {
        projectService.deletePerson(project_id, person_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/find")
    public Project projectByName(@RequestBody @Valid String projectName) {
        if (projectRepository.findByName(projectName).isPresent()){
            return projectRepository.findByName(projectName).get();
        }
        else {
            throw new ProjectNotFoundException();
        }
    }
}
