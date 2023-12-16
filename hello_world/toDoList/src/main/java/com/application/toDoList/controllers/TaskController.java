package com.application.toDoList.controllers;

import com.application.toDoList.dto.TaskToSave;
import com.application.toDoList.services.TaskServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {
    private final TaskServiceImpl taskService;

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks() {
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }

//    @PostMapping("/save")
//    public ResponseEntity<?> saveTask(@RequestBody Task task) {
//        return new ResponseEntity<>(taskService.save(task), HttpStatus.OK);
//    }

    @PostMapping("/save")
    public ResponseEntity<?> saveTask(@RequestBody TaskToSave taskToSave) {
        return new ResponseEntity<>(taskService.save(taskToSave), HttpStatus.OK);
    }

}
