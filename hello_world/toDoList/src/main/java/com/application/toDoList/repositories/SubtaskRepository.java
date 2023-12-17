package com.application.toDoList.repositories;

import com.application.toDoList.domains.Subtask;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubtaskRepository extends MongoRepository<Subtask, String> {
}
