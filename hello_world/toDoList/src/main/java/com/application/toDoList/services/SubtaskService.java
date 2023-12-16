package com.application.toDoList.services;

import com.application.toDoList.domains.Subtask;
import com.application.toDoList.dto.SubtaskToSave;
import com.application.toDoList.repositories.SubtaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SubtaskService {
    private final SubtaskRepository subtaskRepository;

    public Subtask create(SubtaskToSave subtaskToSave) {
        Subtask subtask = new Subtask();
        subtask.setTitle(subtaskToSave.getTitle());
        subtask.setDateOfCreation(LocalDateTime.now());
        subtask.setStatus(Boolean.FALSE);

        return subtaskRepository.save(subtask);
    }
}
