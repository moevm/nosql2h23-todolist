package com.application.toDoList.services;

import com.application.toDoList.domains.Task;
import com.application.toDoList.dto.StatsForAllProjects;
import com.application.toDoList.dto.StatsForEachProject;
import com.application.toDoList.enums.TaskStatus;
import com.application.toDoList.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class StatisticService {
    private final ProjectRepository projectRepository;

    public StatsForAllProjects createStatistics() {
        StatsForAllProjects statsForAll = new StatsForAllProjects();
        statsForAll.setStatistics(new ArrayList<>());

        projectRepository.findAll().forEach(project -> {
            int incomplete = 0;
            int complete = 0;
            for (Task task : project.getTasks()) {
                if (task.getStatus().equals(TaskStatus.INCOMPLETE))
                    incomplete++;

                if (task.getStatus().equals(TaskStatus.COMPLETE))
                    complete++;
            }

            statsForAll.getStatistics().add(new StatsForEachProject(
                    project.getName(),
                    incomplete,
                    complete
                    ));
        });

        return statsForAll;
    }
}
