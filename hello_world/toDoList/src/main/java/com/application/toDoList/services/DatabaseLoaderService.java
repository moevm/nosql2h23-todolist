package com.application.toDoList.services;

import com.application.toDoList.domains.Project;
import com.application.toDoList.repositories.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class DatabaseLoaderService {

    private final ProjectRepository projectRepository;

    @Autowired
    public DatabaseLoaderService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void loadDataFromFile(InputStream inputStream) {
        try {
            // Используем ObjectMapper для чтения JSON из файла
            ObjectMapper objectMapper = new ObjectMapper();
            List<Project> projects = List.of(objectMapper.readValue(inputStream, Project[].class));

            // Сохраняем данные в базу данных
            projectRepository.saveAll(projects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
