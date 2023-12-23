package com.application.toDoList.controllers;

import com.application.toDoList.services.DatabaseLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/data")
public class DatabaseLoaderController {
    private final DatabaseLoaderService databaseLoaderService;

    @Autowired
    public DatabaseLoaderController(DatabaseLoaderService databaseLoaderService) {
        this.databaseLoaderService = databaseLoaderService;
    }

    @GetMapping("/download")
    public ResponseEntity<?> download() {
        byte[] file = databaseLoaderService.saveDataToJsonFile();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "ToDoList.txt");
        return ResponseEntity.ok().headers(headers).body(file);
    }

    @PostMapping("/load")
    public ResponseEntity<HttpStatus> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            databaseLoaderService.loadDataFromFile(file.getInputStream());
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
