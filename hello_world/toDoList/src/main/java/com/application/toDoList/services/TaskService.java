package com.application.toDoList.services;

import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.TaskToSave;
import com.application.toDoList.exceptions.TaskNotFoundException;
import com.application.toDoList.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task create(TaskToSave taskToSave) {
        Task task = new Task();
        task.setTitle(taskToSave.getTitle());
        task.setDateOfCreation(LocalDateTime.now());
        task.setDateOfDeadline(taskToSave.getDateOfDeadline());
        task.setStatus(taskToSave.getStatus());
//        task.setExecuter(taskToSave.getExecuter());
        task.setSubtasks(new ArrayList<>());

        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(String taskId) {
        Optional<Task> foundTask = taskRepository.findById(taskId);
        return foundTask.orElseThrow(TaskNotFoundException::new);
    }

    public Task addSubtaskToTask(String taskId, Subtask subtask) {
        Task task = findById(taskId);
        task.getSubtasks().add(subtask);
        return taskRepository.save(task);
    }
 }
