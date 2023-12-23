package com.application.toDoList.domains;

import com.application.toDoList.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Task")
public class Task {
    @Id
    private String id;
    private String title;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime dateOfCreation;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime dateOfDeadline;
    private TaskStatus status;
    private Person creator;
    private Person executer;
    private ArrayList<Subtask> subtasks;

    public Task(String title, LocalDateTime dateOfCreation, LocalDateTime dateOfDeadline, TaskStatus status, Person creator, Person executer, ArrayList<Subtask> subtasks) {
        this.title = title;
        this.dateOfCreation = dateOfCreation;
        this.dateOfDeadline = dateOfDeadline;
        this.status = status;
        this.creator = creator;
        this.executer = executer;
        this.subtasks = subtasks;
    }
}