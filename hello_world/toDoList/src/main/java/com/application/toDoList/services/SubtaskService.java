package com.application.toDoList.services;

import com.application.toDoList.domains.Project;
import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.SubtaskToSave;
import com.application.toDoList.dto.SubtaskToUpdate;
import com.application.toDoList.exceptions.ProjectNotFoundException;
import com.application.toDoList.exceptions.SubtaskNotFoundException;
import com.application.toDoList.exceptions.TaskNotFoundException;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.repositories.SubtaskRepository;
import com.application.toDoList.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SubtaskService {
    private final SubtaskRepository subtaskRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public Subtask create(String project_id, String task_id, SubtaskToSave subtaskToSave) {
        if (taskRepository.findById(task_id).isEmpty())
            throw new TaskNotFoundException();

        if (projectRepository.findById(project_id).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(project_id).get();
        Task task = taskRepository.findById(task_id).get();

        Subtask subtask = new Subtask();
        subtask.setTitle(subtaskToSave.getTitle());
        subtask.setDateOfCreation(LocalDateTime.now());
        subtask.setStatus(Boolean.FALSE);

        Subtask newSubtask = subtaskRepository.save(subtask);

        task.getSubtasks().add(newSubtask);

        project.getTasks().forEach(taskInList -> {
            if (taskInList.getId().equals(task_id)) {
                taskInList.getSubtasks().add(newSubtask);
            }
        });
        projectRepository.save(project);
        taskRepository.save(task);

        return newSubtask;
    }

    public Subtask updateSubtask(String project_id, String task_id, String subtask_id, SubtaskToUpdate subtaskToUpdate) {
        if (subtaskRepository.findById(subtask_id).isEmpty())
            throw new SubtaskNotFoundException();

        if (taskRepository.findById(task_id).isEmpty())
            throw new TaskNotFoundException();

        if (projectRepository.findById(project_id).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(project_id).get();
        Task task = taskRepository.findById(task_id).get();
        Subtask subtask = subtaskRepository.findById(subtask_id).get();

        subtask.setTitle(subtaskToUpdate.getTitle());
        subtask.setStatus(subtaskToUpdate.getStatus());

        Subtask updatedSubtask = subtaskRepository.save(subtask);

        project.getTasks().forEach(taskInList -> {
            if (taskInList.getId().equals(task.getId())) {
                taskInList.getSubtasks().forEach(subtaskInList -> {
                    if (subtaskInList.getId().equals(subtask.getId())) {
                        subtaskInList.setTitle(subtaskToUpdate.getTitle());
                        subtaskInList.setStatus(subtaskToUpdate.getStatus());
                    }
                });
            }
        });

        task.getSubtasks().forEach(subtaskInList -> {
            if (subtaskInList.getId().equals(subtask.getId())) {
                subtaskInList.setTitle(subtaskToUpdate.getTitle());
                subtaskInList.setStatus(subtaskToUpdate.getStatus());
            }
        });

        projectRepository.save(project);
        taskRepository.save(task);

        return updatedSubtask;
    }

    public Subtask updateSubtask(String project_id, String task_id, String subtask_id, Boolean status) {
        if (subtaskRepository.findById(subtask_id).isEmpty())
            throw new SubtaskNotFoundException();

        if (taskRepository.findById(task_id).isEmpty())
            throw new TaskNotFoundException();

        if (projectRepository.findById(project_id).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(project_id).get();
        Task task = taskRepository.findById(task_id).get();
        Subtask subtask = subtaskRepository.findById(subtask_id).get();

        subtask.setStatus(status);

        Subtask updatedSubtask = subtaskRepository.save(subtask);

        project.getTasks().forEach(taskInList -> {
            if (taskInList.getId().equals(task.getId())) {
                taskInList.getSubtasks().forEach(subtaskInList -> {
                    if (subtaskInList.getId().equals(subtask.getId())) {
                        subtaskInList.setStatus(status);
                    }
                });
            }
        });

        task.getSubtasks().forEach(subtaskInList -> {
            if (subtaskInList.getId().equals(subtask.getId())) {
                subtaskInList.setStatus(status);
            }
        });

        projectRepository.save(project);
        taskRepository.save(task);

        return updatedSubtask;
    }

    public void deleteSubtask(String project_id, String task_id, String subtask_id) {
        if (subtaskRepository.findById(subtask_id).isEmpty())
            throw new SubtaskNotFoundException();

        if (taskRepository.findById(task_id).isEmpty())
            throw new TaskNotFoundException();

        if (projectRepository.findById(project_id).isEmpty())
            throw new ProjectNotFoundException();

        Project project = projectRepository.findById(project_id).get();
        Task task = taskRepository.findById(task_id).get();
        Subtask subtask = subtaskRepository.findById(subtask_id).get();

        task.getSubtasks().remove(subtask);

        project.getTasks().forEach(taskInList -> {
            if (taskInList.getId().equals(task.getId())) {
                taskInList.getSubtasks().remove(subtask);
            }
        });
        subtaskRepository.delete(subtask);
        taskRepository.save(task);
        projectRepository.save(project);
    }
}
