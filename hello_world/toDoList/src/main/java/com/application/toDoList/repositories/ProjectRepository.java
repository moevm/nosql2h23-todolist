package com.application.toDoList.repositories;

import com.application.toDoList.domains.Project;
import com.application.toDoList.domains.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends MongoRepository<Project, String> {
    Optional<Project> findByName(String name);
    Optional<Project> findById(String id);
    void deleteById(String id);

    List<Project> findAllByStatus(String status);
}
