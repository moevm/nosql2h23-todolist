package com.application.toDoList.services;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.TaskToSave;
import com.application.toDoList.dto.TaskToUpdate;
import com.application.toDoList.enums.TaskStatus;
import com.application.toDoList.exceptions.PersonNotFoundException;
import com.application.toDoList.exceptions.SubtaskNotFoundException;
import com.application.toDoList.exceptions.TaskNotFoundException;
import com.application.toDoList.repositories.PersonRepository;
import com.application.toDoList.repositories.SubtaskRepository;
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
    private final PersonRepository personRepository;
    private final SubtaskRepository subtaskRepository;

    public Task create(TaskToSave taskToSave) {
        Task task = new Task();
        task.setTitle(taskToSave.getTitle());
        task.setDateOfCreation(LocalDateTime.now());
        task.setDateOfDeadline(taskToSave.getDateOfDeadline());

        task.setStatus(Enum.valueOf(TaskStatus.class, "INCOMPLETE"));

        task.setSubtasks(new ArrayList<>());

        addExecuterToTask(task, taskToSave.getExecuterId());

        return taskRepository.save(task);
    }

    public Task updateTaskInDB(String taskId) {
        if (taskRepository.findById(taskId).isEmpty())
            throw new TaskNotFoundException();

        Task task = taskRepository.findById(taskId).get();

        return taskRepository.save(task);
    }

    public void addExecuterToTask(Task task, String executerId) {
        if (personRepository.findById(executerId).isEmpty())
            throw new PersonNotFoundException();

        Person person = personRepository.findById(executerId).get();
        task.setExecuter(person);
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

    public Task updateTask(String taskId, TaskToUpdate taskToUpdate) {
        if (taskRepository.findById(taskId).isEmpty())
            throw new TaskNotFoundException();

        Task task = taskRepository.findById(taskId).get();
        task.setTitle(taskToUpdate.getTitle());

        task.setStatus(Enum.valueOf(TaskStatus.class, taskToUpdate.getStatus()));

        task.setDateOfDeadline(taskToUpdate.getDateOfDeadline());

        return taskRepository.save(task);
    }

    public void deleteSubtask(String taskId, String subtaskId) {
        if (taskRepository.findById(taskId).isEmpty())
            throw new TaskNotFoundException();

        if (subtaskRepository.findById(subtaskId).isEmpty())
            throw new SubtaskNotFoundException();

        Task task = taskRepository.findById(taskId).get();
        Subtask subtask = subtaskRepository.findById(subtaskId).get();

        task.getSubtasks().remove(subtask);
        taskRepository.save(task);
    }

    public ArrayList<Subtask> findAllSubtasks(String taskId) {
        if (taskRepository.findById(taskId).isEmpty())
            throw new TaskNotFoundException();

        Task task = taskRepository.findById(taskId).get();
        return task.getSubtasks();
    }
 }
