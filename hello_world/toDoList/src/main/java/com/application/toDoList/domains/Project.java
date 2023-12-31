package com.application.toDoList.domains;

import com.application.toDoList.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Project")
public class Project {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private ProjectStatus status;
    private Set<Person> executors;
    private ArrayList<Task> tasks;

    public Project(String id, String name, ProjectStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.executors = new HashSet<>();
        this.tasks = new ArrayList<>();
    }

    public Project(String name, ProjectStatus status, Set<Person> executors, ArrayList<Task> tasks) {
        this.name = name;
        this.status = status;
        this.executors = executors;
        this.tasks = tasks;
    }
}
