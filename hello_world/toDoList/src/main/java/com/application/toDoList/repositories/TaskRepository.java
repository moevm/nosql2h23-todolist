package com.application.toDoList.repositories;

import com.application.toDoList.domains.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {
    Optional<Task> findById(String id);
//    Optional<List<Task>> findAllByProjectId(String projectId, Pageable page);
//    Optional<List<Task>> findAllByExecuterId(String projectId, String executerId, Pageable page);

    Optional<List<Task>> findAllByExecuterId(String executerId, Pageable page);
}
