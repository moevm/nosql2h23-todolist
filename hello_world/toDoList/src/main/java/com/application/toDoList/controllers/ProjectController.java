package com.application.toDoList.controllers;

import com.application.toDoList.domains.Person;
import com.application.toDoList.domains.Project;
import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.ProjectDTO;
import com.application.toDoList.enums.ProjectStatus;
import com.application.toDoList.exceptions.ProjectNotFoundException;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectRepository projectRepository,  ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    @GetMapping("/admin/all")
    public List<Project> findAll() {
        return projectService.findAll();
    }

    @GetMapping("/admin/find/{person_id}")
    public List<Project> findAllForPerson(@PathVariable("person_id") String person_id) {
        return projectService.findAllForPerson(person_id);
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

    @PostMapping("/admin/task/{task_id}")
    public Task taskToProject(@PathVariable("task_id") String task_id) {
        return projectService.addTask(task_id); //taskDTO
    }

    @DeleteMapping("/admin/task/{task_id}")
    public ResponseEntity<HttpStatus> taskOutProject(@PathVariable("task_id") String task_id) {
        projectService.deleteTask(task_id);
        return ResponseEntity.ok(HttpStatus.OK);
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

    @GetMapping("/find")
    public Project projectByName(String projectName) {
        if (projectRepository.findByName(projectName).isPresent()){
            return projectRepository.findByName(projectName).get();
        }
        else {
            throw new ProjectNotFoundException();
        }
    }

    public ProjectDTO convertToProjectDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName(), project.getStatus().name());
    }

    public Project convertToProject(ProjectDTO projectDTO) {
        return new Project(projectDTO.getId(), projectDTO.getName(), ProjectStatus.valueOf(projectDTO.getStatus()));
    }

}
