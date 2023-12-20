package com.application.toDoList.controllers;

import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.SubtaskToSave;
import com.application.toDoList.dto.TaskToSave;
import com.application.toDoList.dto.TaskToUpdate;
import com.application.toDoList.services.ProjectService;
import com.application.toDoList.services.SubtaskService;
import com.application.toDoList.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final SubtaskService subtaskService;
    private final ProjectService projectService;


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
        return taskService.findAll();
    }

    @PostMapping("/save-task")
    public Task saveNewTask(@RequestBody TaskToSave taskToSave) {
        return taskService.create(taskToSave);
    }

    @PostMapping("/{project_id}")
    public Task addTaskToProject(@RequestBody TaskToSave taskToSave,
                                 @PathVariable("project_id") String project_id) {
        Task newTask = taskService.create(taskToSave);

        return projectService.addTask(newTask.getId(), project_id);
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

    @PostMapping("/add-subtask")
    public Task addNewSubtaskToTask(@RequestBody SubtaskToSave subtaskToSave) {
        Subtask subtask = subtaskService.create(subtaskToSave);
        Task task = taskService.addSubtaskToTask(subtaskToSave.getTaskId(), subtask);
        return task;
    }

    @PatchMapping ("/{project_id}/{task_id}")
    public Task updateTask(@PathVariable("project_id") String project_id,
                           @PathVariable("task_id") String task_id,
                           @RequestBody TaskToUpdate taskToUpdate) {
        Task existingTask = taskService.updateTask(task_id, taskToUpdate);

        projectService.updateProject(project_id);
        return existingTask;
    }
}
