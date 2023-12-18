package com.application.toDoList.dto;

import javax.validation.constraints.NotEmpty;

public class AuthenticationDTO {
    @NotEmpty(message = "Username shouldn't be empty")
    private String username; // Имя пользователя - формат Email
    @NotEmpty(message = "Password shouldn't be empty")

    private String password; // Пароль - кодируется Bcrypt

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}