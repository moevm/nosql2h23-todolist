package com.application.toDoList.dto;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskToSave {
    private String id;
    private String title;
    private Project project;
    private String status;
//    private Person executer;
}
