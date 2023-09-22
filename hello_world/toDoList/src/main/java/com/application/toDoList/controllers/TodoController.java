package com.application.toDoList.controllers;

import com.application.toDoList.domains.Todo;
import com.application.toDoList.repositories.TodoRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @PostMapping("/todos")
    public Todo createTodo() {
        Todo todo = new Todo();
        todo.setText("hello word!");
        return todoRepository.save(todo);
    }
}
