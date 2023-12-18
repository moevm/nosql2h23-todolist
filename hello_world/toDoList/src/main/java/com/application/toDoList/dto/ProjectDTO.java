package com.application.toDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    @NotNull
    private String id;
    @NotNull
    private String name;
    private String status;

    public ProjectDTO(String name, String status) {
        this.name = name;
        this.status = status;
    }
}
