package com.application.toDoList.controllers;

import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.SubtaskToSave;
import com.application.toDoList.dto.TaskToSave;
import com.application.toDoList.dto.TaskToUpdate;
import com.application.toDoList.security.PersonDetails;
import com.application.toDoList.services.PersonService;
import com.application.toDoList.services.ProjectService;
import com.application.toDoList.services.SubtaskService;
import com.application.toDoList.services.TaskService;
import lombok.AllArgsConstructor;
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


    /**
     *
     * Методы для Tasks
     */

    @GetMapping("/{project_id}")
    public List<Task> getAllTasks(@PathVariable("project_id") String project_id) {
        return projectService.findAllTasks(project_id);
    }

    @GetMapping("/all")
    public List<Task> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        if (Objects.equals(personService.findEmail(personDetails.getUsername()).getRole(), "ROLE_ADMIN")) {
            return taskService.findAll();
        }

        return taskService.findAllForPerson(personService.findEmail(personDetails.getUsername()).getId());
    }

    @GetMapping("/all/{project_id}")
    public List<Task> findAllForPersonForProject(@PathVariable("project_id") String project_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return  taskService.findAllTasksForPersonForProject(personService.
                findEmail(personDetails.getUsername()).getId(),
                project_id);
    }

    @GetMapping("/{person_id}")
    public List<Task> findAllForPerson(@PathVariable("person_id") String person_id) {
        return taskService.findAllForPerson(person_id);
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

        return taskService.updateTask(task_id, project_id, taskToUpdate);
    }
}
