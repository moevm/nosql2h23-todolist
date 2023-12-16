package com.application.toDoList.controllers;

import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.SubtaskToSave;
import com.application.toDoList.dto.TaskToSave;
import com.application.toDoList.services.SubtaskService;
import com.application.toDoList.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final SubtaskService subtaskService;

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/save-task")
    public ResponseEntity<?> saveNewTask(@RequestBody TaskToSave taskToSave) {
        return new ResponseEntity<>(taskService.create(taskToSave), HttpStatus.OK);
    }

    @GetMapping("/{taskId}") ResponseEntity<?> getTaskById(@PathVariable("taskId") String taskId) {
        return new ResponseEntity<>(taskService.findById(taskId), HttpStatus.OK);
    }

    @PostMapping("/add-subtask")
    public ResponseEntity<?> addNewSubtaskToTask(@RequestBody SubtaskToSave subtaskToSave) {
        Subtask subtask = subtaskService.create(subtaskToSave);
        Task task = taskService.addSubtaskToTask(subtaskToSave.getTaskId(), subtask);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
