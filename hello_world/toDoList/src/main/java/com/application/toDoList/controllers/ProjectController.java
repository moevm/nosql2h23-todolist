package com.application.toDoList.controllers;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.dto.ProjectDTO;
import com.application.toDoList.security.PersonDetails;
import com.application.toDoList.services.PersonService;
import com.application.toDoList.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final PersonService personService;

    private final int PAGE_SIZE = 3;

    @Autowired
    public ProjectController(ProjectService projectService, PersonService personService) {
        this.projectService = projectService;
        this.personService = personService;
    }

    @GetMapping("/all")
    public List<Project> findAll(@RequestParam(defaultValue = "0", required = false)
                                 Integer page,
                                 @RequestParam(defaultValue = "999", required = false)
                                 Integer size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Pageable paging  = PageRequest.of(page, size);

        if (Objects.equals(personService.findEmail(personDetails.getUsername()).getRole(), "ROLE_ADMIN")){
            return projectService.findAll(paging);
        }
        return projectService.findAllForPerson(personService.findEmail(personDetails.getUsername()).getId(), paging);
    }

    @PostMapping("/find_person/{person_id}")
    public List<Project> findAllForPerson(@PathVariable("person_id") String person_id,
                                          @RequestParam(defaultValue = "999", required = false)
                                          Integer size) {

        Pageable paging  = PageRequest.of(size, PAGE_SIZE);
        return projectService.findAllForPerson(person_id, paging);
    }
    @GetMapping("/find_name/{project_name}")
    public Project projectByName(@PathVariable("project_name")  String projectName) {
        return projectService.findByName(projectName);
    }

    @PostMapping("/create")
    public Project createProject(@RequestBody @Valid ProjectDTO projectDTO,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Incorrect project argument.");
        }
        return projectService.create(projectDTO);
    }

    @DeleteMapping("/{project_id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("project_id") String project_id) {
        projectService.delete(project_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{project_id}")
    public Project updateProject(@RequestBody @Valid ProjectDTO projectDTO,
                                 @PathVariable("project_id") String project_id,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Incorrect project argument.");
        }
        return projectService.change(projectDTO, project_id);
    }



    /**
     *
     * Методы для Person
     */


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


}
