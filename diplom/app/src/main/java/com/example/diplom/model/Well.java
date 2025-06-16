package com.example.diplom.model;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.yandex.mapkit.geometry.Point;
import android.util.Log;

import java.util.Date;

@IgnoreExtraProperties
public class Well {
    private String id;
    private String name;
    private String fullName;
    private String responsiblePersonId;
    private GeoPoint location;
    private double depth;
    private double productionRate;
    private String status;
    private Date lastInspection;
    private String notes;
    private int responsiblePersonCount;

    public Well() {
        // Пустой конструктор для Firebase
        this.responsiblePersonCount = 0;
    }

    public Well(String name, String fullName, String responsiblePersonId, Point location, 
                double depth, double productionRate, String status, Date lastInspection, String notes) {
        this.name = name;
        this.fullName = fullName;
        this.responsiblePersonId = responsiblePersonId;
        this.location = convertToGeoPoint(location);
        this.depth = depth;
        this.productionRate = productionRate;
        this.status = status;
        this.lastInspection = lastInspection;
        this.notes = notes;
        this.responsiblePersonCount = 1;
    }

    // Геттеры и сеттеры
    @Exclude
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getResponsiblePersonId() { return responsiblePersonId; }
    public void setResponsiblePersonId(String responsiblePersonId) { 
        this.responsiblePersonId = responsiblePersonId; 
    }
    
    @Exclude
    public Point getLocation() { 
        if (location == null) {
            return null;
        }
        try {
            return new Point(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            Log.e("Well", "Error converting GeoPoint to Point", e);
            return null;
        }
    }
    
    @Exclude
    public void setLocation(Point location) { 
        if (location == null) {
            this.location = null;
            return;
        }
        try {
            this.location = new GeoPoint(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            Log.e("Well", "Error converting Point to GeoPoint", e);
            this.location = null;
        }
    }
    
    private GeoPoint convertToGeoPoint(Point point) {
        if (point == null) {
            return null;
        }
        try {
            return new GeoPoint(point.getLatitude(), point.getLongitude());
        } catch (Exception e) {
            Log.e("Well", "Error converting Point to GeoPoint", e);
            return null;
        }
    }
    
    public double getDepth() { return depth; }
    public void setDepth(double depth) { this.depth = depth; }
    
    public double getProductionRate() { return productionRate; }
    public void setProductionRate(double productionRate) { this.productionRate = productionRate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getLastInspection() { return lastInspection; }
    public void setLastInspection(Date lastInspection) { this.lastInspection = lastInspection; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public int getResponsiblePersonCount() { return responsiblePersonCount; }
    public void setResponsiblePersonCount(int count) { this.responsiblePersonCount = count; }
    public void incrementResponsiblePersonCount() { this.responsiblePersonCount++; }

    // Добавляем методы для работы с GeoPoint напрямую
    public GeoPoint getGeoPoint() {
        return location;
    }

    public void setGeoPoint(GeoPoint location) {
        this.location = location;
    }
} 