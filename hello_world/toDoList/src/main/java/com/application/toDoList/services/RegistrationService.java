package com.application.toDoList.services;

import com.application.toDoList.domains.Person;
import com.application.toDoList.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Зарегистрировать нового пользователя (сохранить в БД, зашифровав пароль)
    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        if (person.getRole() == null) person.setRole("ROLE_USER");
        if (personRepository.findByEmail(person.getEmail()).isEmpty()) personRepository.save(person);
    }

}