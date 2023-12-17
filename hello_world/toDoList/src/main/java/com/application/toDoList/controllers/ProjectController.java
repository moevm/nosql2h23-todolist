package com.application.toDoList.controllers;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.dto.ProjectDTO;
import com.application.toDoList.exceptions.ProjectNotFoundException;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.security.PersonDetails;
import com.application.toDoList.services.PersonService;
import com.application.toDoList.services.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, ProjectService projectService, PersonService personService, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/admin/all")
    public List<Project> findAll() {
        return projectService.findAll();
    }

    @PostMapping("/admin/find/{person_id}")
    public List<Project> findAllForPerson(@PathVariable("person_id") String person_id) {
        return projectService.findAllForPerson(person_id);
    }

    @GetMapping("/all")
    public List<Project> findAllForPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return projectService.findAllForPerson(personService.findEmail(personDetails.getUsername()).getId());
    }
    @PostMapping("/admin/create")
    public Project createProject(@RequestBody @Valid ProjectDTO projectDTO) {
        return projectService.create(projectDTO);
    }

    @DeleteMapping("/admin/delete/{project_id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("project_id") String project_id) {
        projectService.delete(project_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/admin/{project_id}")
    public Project changeProject(@RequestBody @Valid ProjectDTO projectDTO,
                                 @PathVariable("project_id") String project_id) {
        return projectService.change(projectDTO, project_id);
    }


    @PostMapping("/admin/{project_id}/person/{person_id}")
    public Person personToProject(@PathVariable("project_id") String project_id,
                                @PathVariable("person_id") String person_id) {
        return projectService.addPerson(project_id, person_id);
    }

    @DeleteMapping("/admin/{project_id}/person/{person_id}")
    public ResponseEntity<HttpStatus> PersonOutProject(@PathVariable("project_id") String project_id,
                                                       @PathVariable("person_id") String person_id) {
        projectService.deletePerson(project_id, person_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/find")
    public Project projectByName(String projectName) {
        if (projectRepository.findByName(projectName).isPresent()){
            return projectRepository.findByName(projectName).get();
        }
        else {
            throw new ProjectNotFoundException();
        }
    }

    public ProjectDTO convertToProjectDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }

    public Project convertToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

}
