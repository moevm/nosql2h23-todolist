package com.application.toDoList.controllers;

import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.TaskToSave;
import com.application.toDoList.dto.TaskToUpdate;
import com.application.toDoList.security.PersonDetails;
import com.application.toDoList.services.PersonService;
import com.application.toDoList.services.ProjectService;
import com.application.toDoList.services.SubtaskService;
import com.application.toDoList.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final SubtaskService subtaskService;
    private final ProjectService projectService;
    private final PersonService personService;

    private final int PAGE_SIZE = 5;

    /**
     *
     * Методы для Tasks
     */

    @GetMapping("/{project_id}")
    public List<Task> getAllTasks(@PathVariable("project_id") String project_id,
                                  @RequestParam(defaultValue = "0", required = false)
                                  Integer page) {

        Pageable paging  = PageRequest.of(page, PAGE_SIZE);

        return taskService.findAllForProject(project_id, paging);
    }

    @GetMapping("/all")
    public List<Task> findAll(@RequestParam(defaultValue = "0", required = false)
                                  Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        Pageable paging  = PageRequest.of(page, PAGE_SIZE);

        if (Objects.equals(personService.findEmail(personDetails.getUsername()).getRole(), "ROLE_ADMIN")) {
            return taskService.findAll(paging);
        }

        return taskService.findAllForPerson(personService.findEmail(personDetails.getUsername()).getId(), paging);
    }

    @GetMapping("/all/{project_id}")
    public List<Task> findAllForPersonForProject(@PathVariable("project_id") String project_id,
                                                 @RequestParam(defaultValue= "0", required = false)
                                                 Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        Pageable paging = PageRequest.of(page, PAGE_SIZE);

        return  taskService.findAllTasksForPersonForProject(personService.
                findEmail(personDetails.getUsername()).getId(),
                project_id, paging);
    }

    @GetMapping("/{person_id}")
    public List<Task> findAllForPerson(@PathVariable("person_id") String person_id,
                                       @RequestParam(defaultValue= "0", required = false)
                                       Integer page ,
                                       @RequestParam(defaultValue= "5", required = false)
                                           Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);

        return taskService.findAllForPerson(person_id, paging);
    }

    @PostMapping("/{project_id}")
    public Task addTaskToProject(@RequestBody TaskToSave taskToSave,
                                 @PathVariable("project_id") String project_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        String person_id = personService.findEmail(personDetails.getUsername()).getId();

        return taskService.create(taskToSave, project_id, person_id);
    }

    @GetMapping("/{taskId}")
    Task getTaskById(@PathVariable("taskId") String taskId) {
        return taskService.findById(taskId);
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        if (Objects.equals(personService.findEmail(personDetails.getUsername()).getRole(), "ROLE_ADMIN")) {
            return taskService.updateTask(task_id, project_id, taskToUpdate);
        }

        return taskService.updateTask(task_id, project_id, taskToUpdate.getStatus());
    }
}
