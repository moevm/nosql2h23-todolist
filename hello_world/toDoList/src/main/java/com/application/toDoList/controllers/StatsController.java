package com.application.toDoList.controllers;

import com.application.toDoList.dto.StatsForAllProjects;
import com.application.toDoList.services.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/statistics")
@AllArgsConstructor
public class StatsController {
    private final StatisticService statisticService;

    @GetMapping("/all")
    public StatsForAllProjects getStatistics() {
        return statisticService.createStatistics();
    }
}
