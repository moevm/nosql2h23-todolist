package com.application.toDoList.repositories;

import com.application.toDoList.domains.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {
    Optional<Task> findById(String id);
    
}
