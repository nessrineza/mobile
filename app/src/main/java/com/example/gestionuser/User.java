package com.example.gestionuser;

import androidx.annotation.NonNull;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String userType;

    public User(int id, String username, String email, String password, String userType) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Getters and setters for class members

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @NonNull
    @Override
    public String toString() {
        return username;
    }
}
