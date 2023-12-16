package com.application.toDoList.services;

import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.TaskToSave;

import java.util.List;

public interface TaskService {
    Task save(Task task);
    Task save(TaskToSave taskToSave);

    List<Task> findAll();
}
