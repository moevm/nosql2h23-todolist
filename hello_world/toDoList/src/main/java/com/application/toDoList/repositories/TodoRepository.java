package com.application.toDoList.repositories;

import com.application.toDoList.domains.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, String> {

}
