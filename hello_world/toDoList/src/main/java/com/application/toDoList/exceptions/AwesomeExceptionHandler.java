package com.application.toDoList.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AwesomeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProjectNotFoundException.class)
    protected ResponseEntity<AwesomeException> handleThereIsNoSuchProjectException() {
        return new ResponseEntity<>(new AwesomeException("Проект не найден."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNameException.class)
    protected ResponseEntity<AwesomeException> handleProjectNameException() {
        return new ResponseEntity<>(new AwesomeException("Имя проекта должно быть уникальным."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    protected ResponseEntity<AwesomeException> handleIsNoSuchUserException() {
        return new ResponseEntity<>(new AwesomeException("Пользователь не найден."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    protected ResponseEntity<AwesomeException> handleTaskNotFoundException() {
        return new ResponseEntity<>(new AwesomeException("Задачи не существует."), HttpStatus.NOT_FOUND);
    }


    private static class AwesomeException {
        private String message;

        public AwesomeException(String message) {
            this.message = message;
        }

        public AwesomeException() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}