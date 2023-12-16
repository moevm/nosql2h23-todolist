package com.application.toDoList.repositories;

import com.application.toDoList.domains.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProjectRepository extends MongoRepository<Project, String> {
    Optional<Project> findByName(String name);
    Optional<Project> findById(String id);
    void deleteById(String id);
}
