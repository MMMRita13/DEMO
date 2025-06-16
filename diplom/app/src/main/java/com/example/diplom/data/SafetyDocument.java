package com.example.diplom.data;

import java.util.Date;

public class SafetyDocument {
    private String id;
    private String title;
    private String description;
    private String filePath;
    private Date date;
    private String category;

    public SafetyDocument() {
        // Пустой конструктор для Firebase
    }

    public SafetyDocument(String id, String title, String description, String filePath, Date date, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.date = date;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
} 