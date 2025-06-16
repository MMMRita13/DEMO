package com.example.diplom;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.yandex.mapkit.MapKitFactory;

public class GazpromApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Инициализация Firebase
        FirebaseApp.initializeApp(this);
        
        // Настройка Firestore
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        firestore.setFirestoreSettings(settings);
        
        // Инициализация Яндекс Карт
        MapKitFactory.setApiKey("bdf5cb1d-0c4e-4731-91be-71617047f2db");
        MapKitFactory.initialize(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // Дополнительная инициализация при необходимости
    }
} 