package com.example.diplom.data;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Employee {
    private String id;
    private String name;
    private String position;
    private String department;
    private String email;
    private String phone;
    private boolean status;

    // Пустой конструктор для Firestore
    public Employee() {}

    public Employee(String name, String position, String department, String email, String phone, boolean status) {
        this.name = name;
        this.position = position;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
} 