package com.application.toDoList.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Subtask")
public class Subtask {
    @Id
    private String id;
    private String title;
    private LocalDateTime dateOfCreation;
    private Boolean status;
}