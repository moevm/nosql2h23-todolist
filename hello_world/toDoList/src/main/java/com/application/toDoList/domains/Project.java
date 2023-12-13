package com.application.toDoList.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "demo")
public class Project {
    @Id
    private String id;
    private String name;
    private String status;
    private Set<Person> executors;
    private ArrayList<Task> tasks;
    private Logging log;


}
