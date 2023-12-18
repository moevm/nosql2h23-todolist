package com.application.toDoList.controllers;

import com.application.toDoList.domains.Subtask;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.SubtaskToSave;
import com.application.toDoList.dto.SubtaskToUpdate;
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

@RestController
@AllArgsConstructor
@RequestMapping("/subtasks")
public class SubtaskController {
    private final TaskService taskService;
    private final SubtaskService subtaskService;
    private final ProjectService projectService;


    /**
     *
     * Методы для subtask
     */
    @GetMapping("/{project_id}/{task_id}/subtasks")
    public List<Subtask> getAllSubtasksOfTask(@PathVariable("project_id") String project_id,
                                              @PathVariable("task_id") String task_id)         {
        return taskService.findAllSubtasks(task_id);
    }

    @PostMapping("/{project_id}/{task_id}/add-subtask")
    public ResponseEntity<?> addNewSubtaskToTask(@PathVariable("project_id") String project_id,
                                                 @PathVariable("task_id") String task_id,
                                                 @RequestBody SubtaskToSave subtaskToSave) {
        Subtask subtask = subtaskService.create(subtaskToSave);
        Task task = taskService.addSubtaskToTask(task_id, subtask);
        projectService.updateProject(project_id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PatchMapping("/{project_id}/{task_id}/{subtask_id}")
    public ResponseEntity<?> updateSubtask(@PathVariable("project_id") String project_id,
                                           @PathVariable("task_id") String task_id,
                                           @PathVariable("subtask_id") String subtask_id,
                                           @RequestBody SubtaskToUpdate subtaskToUpdate) {
        Subtask subtask = subtaskService.updateSubtask(subtask_id, subtaskToUpdate);

        taskService.uodateTaskInDB(task_id);
        projectService.updateProject(project_id);

        return new ResponseEntity<>(subtask, HttpStatus.OK);
    }

    @DeleteMapping("/{project_id}/{task_id}/{subtask_id}")
    public ResponseEntity<?> updateSubtask(@PathVariable("project_id") String project_id,
                                           @PathVariable("task_id") String task_id,
                                           @PathVariable("subtask_id") String subtask_id) {

        taskService.deleteSubtask(task_id, subtask_id);
        projectService.updateProject(project_id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

}
