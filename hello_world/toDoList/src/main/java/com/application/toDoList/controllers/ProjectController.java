package com.application.toDoList.controllers;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.*;
import com.application.toDoList.exceptions.ProjectNotFoundException;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.security.PersonDetails;
import com.application.toDoList.services.PersonService;
import com.application.toDoList.services.ProjectService;
import com.application.toDoList.services.SubtaskService;
import com.application.toDoList.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final PersonService personService;
    private final TaskService taskService;
    private final SubtaskService subtaskService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, ProjectService projectService, PersonService personService, TaskService taskService, SubtaskService subtaskService, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
        this.personService = personService;
        this.taskService = taskService;
        this.subtaskService = subtaskService;
        this.modelMapper = modelMapper;
    }

    /**
     *
     * Методы для Project
     */
    @GetMapping("/admin/all")
    public List<Project> findAll() {
        return projectService.findAll();
    }

    @GetMapping("/admin/all/{status}")
    public List<Project> getAllByStatus(@PathVariable("status") String status) {
        return projectService.findAllByStatus(status);
    }


    @PostMapping("/admin/create")
    public Project createProject(@RequestBody @Valid ProjectDTO projectDTO) {
        return projectService.create(projectDTO);
    }

    @DeleteMapping("/admin/delete/{project_id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("project_id") String project_id) {
        projectService.delete(project_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/admin/{project_id}")
    public Project changeProject(@RequestBody @Valid ProjectDTO projectDTO,
                                 @PathVariable("project_id") String project_id) {
        return projectService.change(projectDTO, project_id);
    }

    /**
     *
     * Методы для Tasks
     */

    @GetMapping("/admin/{project_id}")
    public List<Task> getAllTasks(@PathVariable("project_id") String project_id) {
        return projectService.findAllTasks(project_id);
    }

    @GetMapping("/admin/{project_id}/incomplete")
    public List<Task> getAllIncompleteTasks(@PathVariable("project_id") String project_id) {
        return projectService.findAllTasks(project_id);
    }

    @PostMapping("/admin/{project_id}")
    public Task addTaskToProject(@RequestBody TaskToSave taskToSave,
                                 @PathVariable("project_id") String project_id) {
        Task newTask = taskService.create(taskToSave);

        return projectService.addTask(newTask.getId(), project_id);
    }

    @DeleteMapping("/admin/{project_id}/{task_id}")
    public ResponseEntity<HttpStatus> deleteTaskFromProject(@PathVariable("project_id") String project_id,
                                                            @PathVariable("task_id") String task_id) {
        projectService.deleteTask(task_id, project_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping ("/admin/{project_id}/{task_id}")
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
    @GetMapping("/admin/{project_id}/{task_id}/subtasks")
    public List<Subtask> getAllSubtasksOfTask(@PathVariable("project_id") String project_id,
                                                   @PathVariable("task_id") String task_id)         {
        return taskService.findAllSubtasks(task_id);
    }

    @PostMapping("/admin/{project_id}/{task_id}/add-subtask")
    public ResponseEntity<?> addNewSubtaskToTask(@PathVariable("project_id") String project_id,
                                                 @PathVariable("task_id") String task_id,
                                                 @RequestBody SubtaskToSave subtaskToSave) {
        Subtask subtask = subtaskService.create(subtaskToSave);
        Task task = taskService.addSubtaskToTask(task_id, subtask);
        projectService.updateProject(project_id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PatchMapping("/admin/{project_id}/{task_id}/{subtask_id}")
    public ResponseEntity<?> updateSubtask(@PathVariable("project_id") String project_id,
                                                 @PathVariable("task_id") String task_id,
                                                 @PathVariable("subtask_id") String subtask_id,
                                                 @RequestBody SubtaskToUpdate subtaskToUpdate) {
        Subtask subtask = subtaskService.updateSubtask(subtask_id, subtaskToUpdate);

        taskService.uodateTaskInDB(task_id);
        projectService.updateProject(project_id);

        return new ResponseEntity<>(subtask, HttpStatus.OK);
    }

    @DeleteMapping("/admin/{project_id}/{task_id}/{subtask_id}")
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

    @PostMapping("/admin/find/{person_id}")
    public List<Project> findAllForPerson(@PathVariable("person_id") String person_id) {
        return projectService.findAllForPerson(person_id);
    }

    @GetMapping("/all")
    public List<Project> findAllForPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return projectService.findAllForPerson(personService.findEmail(personDetails.getUsername()).getId());
    }



    @PostMapping("/admin/{project_id}/person/{person_id}")
    public Person personToProject(@PathVariable("project_id") String project_id,
                                @PathVariable("person_id") String person_id) {
        return projectService.addPerson(project_id, person_id);
    }

    @DeleteMapping("/admin/{project_id}/person/{person_id}")
    public ResponseEntity<HttpStatus> PersonOutProject(@PathVariable("project_id") String project_id,
                                                       @PathVariable("person_id") String person_id) {
        projectService.deletePerson(project_id, person_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/find")
    public Project projectByName(String projectName) {
        if (projectRepository.findByName(projectName).isPresent()){
            return projectRepository.findByName(projectName).get();
        }
        else {
            throw new ProjectNotFoundException();
        }

    }

    public ProjectDTO convertToProjectDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }

    public Project convertToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

}
