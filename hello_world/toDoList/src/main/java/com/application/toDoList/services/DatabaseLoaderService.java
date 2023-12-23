package com.application.toDoList.services;

import com.application.toDoList.domains.Project;
import com.application.toDoList.repositories.PersonRepository;
import com.application.toDoList.repositories.ProjectRepository;
import com.application.toDoList.repositories.SubtaskRepository;
import com.application.toDoList.repositories.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

@Service
public class DatabaseLoaderService {

    private final ProjectRepository projectRepository;
    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;

    @Autowired
    public DatabaseLoaderService(ProjectRepository projectRepository, PersonRepository personRepository, TaskRepository taskRepository, SubtaskRepository subtaskRepository) {
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
        this.taskRepository = taskRepository;
        this.subtaskRepository = subtaskRepository;
    }

    public void loadDataFromFile(byte[] file) throws DataFormatException {
        try {
            // Используем ObjectMapper для чтения JSON из файла
            ObjectMapper objectMapper = new ObjectMapper();
            List<Project> projects = List.of(objectMapper.readValue(file, Project[].class));
            projectRepository.saveAll(projects);

            projects.forEach(project -> {
                project.getExecutors().forEach(person -> {
                    if (personRepository.findByEmail(person.getEmail()).isEmpty())
                        personRepository.save(person);
                });
                taskRepository.saveAll(project.getTasks());
                project.getTasks().forEach(task -> {
                    subtaskRepository.saveAll(task.getSubtasks());
                });
            });

            // Сохраняем данные в базу данных
            projectRepository.saveAll(projects);
        } catch (IOException e) {
            throw new DataFormatException("Неподходящий формат хранения данных.");
        }
    }
    public byte[] saveDataToJsonFile() {
        try {
            List<Project> projects = projectRepository.findAll();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(projects).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
