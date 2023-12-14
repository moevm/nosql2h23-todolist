package com.application.toDoList.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Person")
public class Person {
    @Id
    private ObjectId id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;

}