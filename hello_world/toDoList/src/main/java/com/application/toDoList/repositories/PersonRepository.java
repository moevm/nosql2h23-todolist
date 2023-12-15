package com.application.toDoList.repositories;

import com.application.toDoList.domains.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {
    Optional<Person> findById(ObjectId id);
}
