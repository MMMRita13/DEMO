package com.example.diplom.ui.map;

import android.content.Context;
import android.util.Log;

import com.example.diplom.R;
import com.example.diplom.model.Well;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.runtime.image.ImageProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapMarkerManager {
    private static final String TAG = "MapMarkerManager";
    
    private final Context context;
    private final MapObjectCollection mapObjects;
    private final Map<String, PlacemarkMapObject> placemarks = new HashMap<>();
    private MapObjectTapListener tapListener;
    
    public MapMarkerManager(Context context, MapObjectCollection mapObjects) {
        this.context = context;
        this.mapObjects = mapObjects;
    }
    
    public void setTapListener(MapObjectTapListener listener) {
        this.tapListener = listener;
    }
    
    public void addWell(Well well) {
        if (well == null || well.getLocation() == null) {
            Log.e(TAG, "Не удалось добавить метку: скважина или координаты null");
            return;
        }
        
        // Проверяем, существует ли уже метка с таким ID
        if (placemarks.containsKey(well.getId())) {
            Log.d(TAG, "Обновляем существующую метку для скважины: " + well.getName());
            updateWell(well);
            return;
        }
        
        try {
            // Создаем новую метку
            PlacemarkMapObject placemark = mapObjects.addPlacemark(well.getLocation());
            if (placemark == null) {
                Log.e(TAG, "Не удалось создать метку для скважины: " + well.getName());
                return;
            }
            
            placemark.setIcon(ImageProvider.fromResource(context, R.drawable.well));
            placemark.setText(well.getName());
            placemark.setUserData(well);
            
            if (tapListener != null) {
                placemark.addTapListener(tapListener);
            }
            
            placemarks.put(well.getId(), placemark);
            Log.d(TAG, "Метка добавлена для скважины: " + well.getName());
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при добавлении метки для скважины " + well.getName(), e);
        }
    }
    
    public void addWells(List<Well> wells) {
        if (wells == null) {
            Log.e(TAG, "Не удалось добавить метки: список скважин null");
            return;
        }
        
        Log.d(TAG, "Добавление " + wells.size() + " скважин на карту");
        for (Well well : wells) {
            addWell(well);
        }
    }
    
    public void removeWell(String wellId) {
        PlacemarkMapObject placemark = placemarks.remove(wellId);
        if (placemark != null) {
            placemark.getParent().remove(placemark);
        }
    }
    
    public void clearAll() {
        for (PlacemarkMapObject placemark : placemarks.values()) {
            placemark.getParent().remove(placemark);
        }
        placemarks.clear();
    }
    
    public void updateWell(Well well) {
        if (well == null) {
            Log.e(TAG, "Не удалось обновить метку: скважина null");
            return;
        }
        
        PlacemarkMapObject placemark = placemarks.get(well.getId());
        if (placemark != null) {
            placemark.setGeometry(well.getLocation());
            placemark.setText(well.getName());
            placemark.setUserData(well);
        } else {
            addWell(well);
        }
    }
} 