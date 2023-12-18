package com.application.toDoList.controllers;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.dto.ProjectDTO;
import com.application.toDoList.exceptions.ProjectNotFoundException;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.security.PersonDetails;
import com.application.toDoList.services.PersonService;
import com.application.toDoList.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final PersonService personService;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, ProjectService projectService, PersonService personService) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
        this.personService = personService;
    }

    @GetMapping("/all")
    public List<Project> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (Objects.equals(personService.findEmail(personDetails.getUsername()).getRole(), "ROLE_ADMIN")){
            return projectService.findAll();
        }
        return projectService.findAllForPerson(personService.findEmail(personDetails.getUsername()).getId());
    }

    @PostMapping("/find/{person_id}")
    public List<Project> findAllForPerson(@PathVariable("person_id") String person_id) {
        return projectService.findAllForPerson(person_id);
    }

    @PostMapping("/create")
    public Project createProject(@RequestBody @Valid ProjectDTO projectDTO,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Incorrect project argument.");
        }
        return projectService.create(projectDTO);
    }

    @DeleteMapping("/delete/{project_id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("project_id") String project_id) {
        projectService.delete(project_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{project_id}")
    public Project changeProject(@RequestBody @Valid ProjectDTO projectDTO,
                                 @PathVariable("project_id") String project_id,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Incorrect project argument.");
        }
        return projectService.change(projectDTO, project_id);
    }


    @PostMapping("/{project_id}/person/{person_id}")
    public Person personToProject(@PathVariable("project_id") String project_id,
                                @PathVariable("person_id") String person_id) {
        return projectService.addPerson(project_id, person_id);
    }

    @DeleteMapping("/{project_id}/person/{person_id}")
    public ResponseEntity<HttpStatus> PersonOutProject(@PathVariable("project_id") String project_id,
                                                       @PathVariable("person_id") String person_id) {
        projectService.deletePerson(project_id, person_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/find")
    public Project projectByName(@RequestBody @Valid String projectName) {
        if (projectRepository.findByName(projectName).isPresent()){
            return projectRepository.findByName(projectName).get();
        }
        else {
            throw new ProjectNotFoundException();
        }
    }
}
