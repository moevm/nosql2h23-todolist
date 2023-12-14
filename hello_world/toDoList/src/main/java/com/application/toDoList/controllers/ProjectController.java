package com.application.toDoList.controllers;

import com.application.toDoList.domains.Project;
import com.application.toDoList.dto.ProjectDTO;
import com.application.toDoList.enums.ProjectStatus;
import com.application.toDoList.repositories.ProjectRepository;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @PostMapping("/admin/create")
    public Project createProject(@RequestBody @Valid ProjectDTO projectDTO) {

        if (projectRepository.findByName(projectDTO.getName()).isPresent()) {
            // ИСКЛЮЧЕНИЕ ЧТО УЖЕ СУЩЕСТВУЕТ
            return new Project();
        }

        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setStatus(Enum.valueOf(ProjectStatus.class, projectDTO.getStatus()));
        // postService.save(convertToPost(postDTO, 0));
        return projectRepository.save(project); //TO DTO
    }

    @PatchMapping("/admin/{project_id}")
    public Project changeProject(@RequestBody @Valid ProjectDTO projectDTO,
                                 @PathVariable("project_id") ObjectId project_id) {
        Project project = new Project();
        if (projectRepository.findById(project_id).isPresent()){
            project = projectRepository.findById(project_id).get();
        }
        else {
            // исключение проект не найден
        }
        if (projectRepository.findByName(projectDTO.getName()).isEmpty()){
            project.setName(projectDTO.getName());
        }
        else{
            // исключение проект с таким названием уже есть
        }
        project.setStatus(Enum.valueOf(ProjectStatus.class, projectDTO.getStatus()));
        return projectRepository.save(project); //TO DTO
    }
 //TO DTO
    // DTO TO
    //установить существующую задачу проекту
    //назначить и удалить на проект человека/людей
        //поиск проекта по названию

    //Разделить интерфейс
}
