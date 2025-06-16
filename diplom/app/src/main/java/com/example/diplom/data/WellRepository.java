package com.example.diplom.data;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import com.example.diplom.model.Well;
import com.yandex.mapkit.geometry.Point;

public class WellRepository {
    private static final String TAG = "WellRepository";
    private static final String COLLECTION_WELLS = "wells";
    
    private final FirebaseFirestore db;

    public WellRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public interface OnWellsLoadedListener {
        void onWellsLoaded(List<Well> wells);
    }

    public interface OnWellSavedListener {
        void onWellSaved(boolean success);
    }

    public interface OnWellLoadedListener {
        void onWellLoaded(Well well);
    }

    public void getWells(OnWellsLoadedListener listener) {
        db.collection(COLLECTION_WELLS)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                List<Well> wells = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Well well = document.toObject(Well.class);
                    well.setId(document.getId());
                    Log.d(TAG, "Loaded well: " + well.getName() + 
                          ", location: " + (well.getLocation() != null ? 
                          String.format("%.6f, %.6f", well.getLocation().getLatitude(), well.getLocation().getLongitude()) : 
                          "null"));
                    wells.add(well);
                }
                listener.onWellsLoaded(wells);
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error loading wells", e);
                listener.onWellsLoaded(new ArrayList<>());
            });
    }

    public void searchWells(String nameQuery, String statusQuery, OnWellsLoadedListener listener) {
        Log.d(TAG, "Searching wells with name: " + nameQuery + ", status: " + statusQuery);
        
        Query query = db.collection(COLLECTION_WELLS);
        
        if (nameQuery != null && !nameQuery.isEmpty()) {
            query = query.whereGreaterThanOrEqualTo("name", nameQuery.toLowerCase())
                       .whereLessThanOrEqualTo("name", nameQuery.toLowerCase() + '\uf8ff');
        }
        
        if (statusQuery != null && !statusQuery.isEmpty()) {
            String lowerStatusQuery = statusQuery.toLowerCase();
            if (lowerStatusQuery.equals("не в работе")) {
                query = query.whereNotEqualTo("status", "В работе");
            } else if (lowerStatusQuery.equals("в работе")) {
                query = query.whereEqualTo("status", "В работе");
            } else if (lowerStatusQuery.equals("закрыта")) {
                query = query.whereEqualTo("status", "Закрыта");
            } else {
                query = query.whereEqualTo("status", statusQuery);
            }
        }
        
        query.get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                List<Well> wells = new ArrayList<>();
                Log.d(TAG, "Found " + queryDocumentSnapshots.size() + " wells");
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Well well = document.toObject(Well.class);
                    well.setId(document.getId());
                    Log.d(TAG, "Loaded well: " + well.getName() + 
                          ", location: " + (well.getLocation() != null ? 
                          String.format("%.6f, %.6f", well.getLocation().getLatitude(), well.getLocation().getLongitude()) : 
                          "null"));
                    wells.add(well);
                    Log.d(TAG, "Added well: " + well.getName() + " with status: " + well.getStatus());
                }
                listener.onWellsLoaded(wells);
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error searching wells", e);
                listener.onWellsLoaded(new ArrayList<>());
            });
    }

    public void saveWell(Well well, OnWellSavedListener listener) {
        Log.d(TAG, "Saving well: " + well.getName() + 
              ", location: " + (well.getLocation() != null ? 
              String.format("%.6f, %.6f", well.getLocation().getLatitude(), well.getLocation().getLongitude()) : 
              "null"));
        Log.d(TAG, "GeoPoint: " + (well.getGeoPoint() != null ? 
              String.format("%.6f, %.6f", well.getGeoPoint().getLatitude(), well.getGeoPoint().getLongitude()) : 
              "null"));
              
        if (well.getId() == null || well.getId().isEmpty()) {
            // Создание новой скважины
            db.collection(COLLECTION_WELLS)
                    .add(well)
                    .addOnSuccessListener(documentReference -> {
                        Log.d(TAG, "Well saved successfully with ID: " + documentReference.getId());
                        listener.onWellSaved(true);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error saving well", e);
                        listener.onWellSaved(false);
                    });
        } else {
            // Обновление существующей скважины
            db.collection(COLLECTION_WELLS)
                    .document(well.getId())
                    .set(well)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Well updated successfully");
                        listener.onWellSaved(true);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error updating well", e);
                        listener.onWellSaved(false);
                    });
        }
    }

    public void deleteWell(String wellId, OnWellSavedListener listener) {
        db.collection(COLLECTION_WELLS)
            .document(wellId)
            .delete()
            .addOnSuccessListener(aVoid -> {
                Log.d(TAG, "Well deleted successfully");
                listener.onWellSaved(true);
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error deleting well", e);
                listener.onWellSaved(false);
            });
    }

    public void getWellById(String wellId, OnWellLoadedListener listener) {
        db.collection("wells")
            .document(wellId)
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    Well well = documentSnapshot.toObject(Well.class);
                    if (well != null) {
                        well.setId(documentSnapshot.getId());
                        listener.onWellLoaded(well);
                    } else {
                        listener.onWellLoaded(null);
                    }
                } else {
                    listener.onWellLoaded(null);
                }
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error getting well by ID", e);
                listener.onWellLoaded(null);
            });
    }
} 