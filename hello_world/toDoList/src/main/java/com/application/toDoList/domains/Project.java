package com.application.toDoList.domains;

import com.application.toDoList.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Project")
public class Project {
    @Id
    private ObjectId id;
    private String name;
    private ProjectStatus status;
    private Set<Person> executors;
    private ArrayList<Task> tasks;
    private Logging log;
}
