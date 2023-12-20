package com.application.toDoList.controllers;

import com.application.toDoList.domains.Person;
import com.application.toDoList.dto.AuthenticationDTO;
import com.application.toDoList.dto.PersonDTO;
import com.application.toDoList.security.JWTUtil;
import com.application.toDoList.security.PersonValidator;
import com.application.toDoList.services.PersonService;
import com.application.toDoList.services.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonService personService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonService personService, PersonValidator personValidator,
                          JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.personService = personService;
        this.jwtUtil = jwtUtil;
        this.personValidator = personValidator;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    //    Регистрация нового пользователя
    @PostMapping("/registration")
    public Map<String, Object> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Collections.singletonMap("message", "Недостаточно данных при регистрации.");
        }

        Person person = convertToPerson(personDTO);

        personValidator.validate(person, bindingResult);


        registrationService.register(person);

        String token = jwtUtil.generateToken(person.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("user", personService.findEmail(person.getEmail()));
        response.put("jwt-token", token);
        return response;
    }

    //  Аутентификация пользователя
    @PostMapping("/login")
    public Map<String, Object> performLogin(@RequestBody @Valid AuthenticationDTO authenticationDTO,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Collections.singletonMap("message", "Недостаточно данных при регистрации.");
        }
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect credentials");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("user", personService.findEmail(authenticationDTO.getUsername()));
        response.put("jwt-token", token);
        return response;
    }

    //    Преобразование PersonDTO -> Person
    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    //    Преобразование Person -> PersonDTO
    public PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}