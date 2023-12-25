package com.application.toDoList.services;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.TaskToSave;
import com.application.toDoList.dto.TaskToUpdate;
import com.application.toDoList.enums.TaskStatus;
import com.application.toDoList.exceptions.PersonNotFoundException;
import com.application.toDoList.exceptions.ProjectNotFoundException;
import com.application.toDoList.exceptions.TaskNotFoundException;
import com.application.toDoList.repositories.PersonRepository;
import com.application.toDoList.repositories.ProjectRepository;
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
    private final ProjectRepository projectRepository;

    public Task create(TaskToSave taskToSave, String project_id, String person_id) {
        if(projectRepository.findById(project_id).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(project_id).get();

        Task task = new Task();
        task.setTitle(taskToSave.getTitle());
        task.setDateOfCreation(LocalDateTime.now());
        task.setDateOfDeadline(taskToSave.getDateOfDeadline());

        task.setStatus(Enum.valueOf(TaskStatus.class, "INCOMPLETE"));

        task.setSubtasks(new ArrayList<>());

        task.setCreator(personRepository.findById(person_id).get());
        addExecuterToTask(task, taskToSave.getExecuterId());

        Task saveTask = taskRepository.save(task);
        project.getTasks().add(task);
        projectRepository.save(project);
        return saveTask;
    }

    public Task updateTask(String taskId, String projectId, TaskToUpdate taskToUpdate) {
        if (taskRepository.findById(taskId).isEmpty())
            throw new TaskNotFoundException();

        if(projectRepository.findById(projectId).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(projectId).get();

        Task task = taskRepository.findById(taskId).get();
        task.setTitle(taskToUpdate.getTitle());
        task.setStatus(Enum.valueOf(TaskStatus.class, taskToUpdate.getStatus()));

        task.setDateOfDeadline(taskToUpdate.getDateOfDeadline());
        task.setExecuter(personRepository.findById(taskToUpdate.getExecuterId()).get());

        Task saveTask = taskRepository.save(task);

        project.getTasks().forEach(taskInList -> {
            if (taskInList.getId().equals(task.getId())) {
                taskInList.setTitle(taskToUpdate.getTitle());
                taskInList.setStatus(Enum.valueOf(TaskStatus.class, taskToUpdate.getStatus()));
                taskInList.setDateOfDeadline(taskToUpdate.getDateOfDeadline());
                taskInList.setExecuter(personRepository.findById(taskToUpdate.getExecuterId()).get());
            }
        });

        projectRepository.save(project);
        return saveTask;
    }

    public Task updateTask(String taskId, String projectId, String taskStatus) {
        if (taskRepository.findById(taskId).isEmpty())
            throw new TaskNotFoundException();

        if(projectRepository.findById(projectId).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(projectId).get();

        Task task = taskRepository.findById(taskId).get();

        task.setStatus(Enum.valueOf(TaskStatus.class, taskStatus));

        project.getTasks().forEach(taskInList -> {
            if (taskInList.getId().equals(task.getId())) {
                taskInList.setStatus(Enum.valueOf(TaskStatus.class, taskStatus));
            }
        });

        Task saveTask = taskRepository.save(task);
        projectRepository.save(project);

        return saveTask;
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

    public List<Task> findAllForPerson(String person_id) {
        if (personRepository.findById(person_id).isEmpty())
            throw new PersonNotFoundException();

        List<Task> allTasksForPerson = new ArrayList<>();

        for (Task task : this.findAll()) {
            if (task.getExecuter() != null && task.getExecuter().getId().equals(person_id)) {
                allTasksForPerson.add(task);
            }
        }

        return allTasksForPerson;
    }

    public List<Task> findAllTasksForPersonForProject(String person_id, String project_id) {
        if (personRepository.findById(person_id).isEmpty())
            throw new PersonNotFoundException();

        if (projectRepository.findById(project_id).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(project_id).get();
        List<Task> allTasksForPerson = new ArrayList<>();

        project.getTasks().forEach(task -> {
            if (task.getExecuter() != null && task.getExecuter().getId().equals(person_id)) {
                allTasksForPerson.add(task);
            }
        });

        return allTasksForPerson;
    }

    public Task findById(String taskId) {
        Optional<Task> foundTask = taskRepository.findById(taskId);
        return foundTask.orElseThrow(TaskNotFoundException::new);
    }

    public ArrayList<Subtask> findAllSubtasks(String taskId) {
        if (taskRepository.findById(taskId).isEmpty())
            throw new TaskNotFoundException();

        Task task = taskRepository.findById(taskId).get();
        return task.getSubtasks();
    }
 }
