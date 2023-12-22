package com.application.toDoList.services;

import com.application.toDoList.domains.Person;
import com.application.toDoList.exceptions.PersonNotFoundException;
import com.application.toDoList.repositories.PersonRepository;
import com.application.toDoList.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    // Поиск в БД человека с заданным email
    public Person findEmail(String email) {
        Optional<Person> foundPerson = personRepository.findByEmail(email);
        return foundPerson.orElseThrow(PersonNotFoundException::new);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return this.findEmail(personDetails.getUsername());
    }

}