package com.application.toDoList.domains;

import com.application.toDoList.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Task")
public class Task {
    @Id
    private String id;
    private String title;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateOfCreation;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
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