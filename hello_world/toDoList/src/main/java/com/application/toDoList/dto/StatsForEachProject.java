package com.application.toDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsForEachProject {
    private String projectName;
    private int incomplete;
    private int complete;
}
