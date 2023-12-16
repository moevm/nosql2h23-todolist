package com.application.toDoList.services;

import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.TaskToSave;
import com.application.toDoList.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;


    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task save(TaskToSave taskToSave) {
        Task task = new Task();
        task.setTitle(taskToSave.getTitle());
        task.setDateOfCreation(LocalDateTime.now());
        // TODO временное решение
        task.setDateOfDeadline(LocalDateTime.now());
        task.setStatus(taskToSave.getStatus());

        task.setSubtasks(new ArrayList<Subtask>());
        // добавить исполнителя и создателя

        // проверить существование проекта
        task.setProject(taskToSave.getProject());
        return save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
