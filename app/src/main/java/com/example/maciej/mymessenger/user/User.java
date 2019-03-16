package com.example.maciej.mymessenger.user;

public class User {

    private String email;
    private String userId;

    public User(String email, String userId) {
        this.email = email;
        this.userId = userId;
    }


    public String getUserId() {
        return userId;
    }


    public String getEmail() {
        return email;
    }
}