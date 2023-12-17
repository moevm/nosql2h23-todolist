package com.application.toDoList.dto;

public class AuthenticationDTO {

    private String username; // Имя пользователя - формат Email

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