package com.example.diplom.data;

public class User {
    private String email;
    private boolean isAdmin;

    public User() {
        // Пустой конструктор для Firestore
    }

    public User(String email, boolean isAdmin) {
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
} 