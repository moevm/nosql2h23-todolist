package com.application.toDoList.security;

import com.application.toDoList.domains.Person;
import com.application.toDoList.exceptions.PersonAlreadyExistsException;
import com.application.toDoList.repositories.PersonRepository;
import com.application.toDoList.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;
    private final PersonRepository personRepository;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService, PersonRepository personRepository) {
        this.personDetailsService = personDetailsService;
        this.personRepository = personRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        System.out.println(person.getEmail());
        System.out.println(personRepository.findByEmail(person.getEmail()).isPresent());
        if (personRepository.findByEmail(person.getEmail()).isEmpty()) return;
        throw new PersonAlreadyExistsException();
    }
}
