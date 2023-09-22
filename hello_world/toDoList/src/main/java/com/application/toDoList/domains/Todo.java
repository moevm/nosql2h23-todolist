package com.application.toDoList.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "demo")
public class Todo {
    @Id
    private String id;
    private String text;

    // геттеры и сеттеры
}
