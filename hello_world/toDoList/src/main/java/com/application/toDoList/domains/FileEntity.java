package com.application.toDoList.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    private String fileName;
    private byte[] fileContent;

}
