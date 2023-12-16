package com.application.toDoList.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
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
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfDeadline;
    private String status;
//    private Person creator;
//    private Person executer;
    private ArrayList<Subtask> subtasks;
    private Project project;

}