package com.application.toDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private String id;
    private String name;
    private String status;

    public ProjectDTO(String name, String status) {
        this.name = name;
        this.status = status;
    }
}
