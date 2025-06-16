package com.example.diplom.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import android.util.Log;

public class UserRepository {
    private static final String COLLECTION_USERS = "users";
    private static final String FIELD_IS_ADMIN = "isAdmin";

    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    public UserRepository() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void checkUserRole(OnUserRoleCheckedListener listener) {
        String userId = auth.getCurrentUser().getUid();
        
        db.collection(COLLECTION_USERS)
            .document(userId)
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    boolean isAdmin = Boolean.TRUE.equals(documentSnapshot.getBoolean(FIELD_IS_ADMIN));
                    listener.onUserRoleChecked(isAdmin);
                } else {
                    // Если документ не существует, создаем его с ролью обычного пользователя
                    db.collection(COLLECTION_USERS)
                        .document(userId)
                        .set(new User(auth.getCurrentUser().getEmail(), false))
                        .addOnSuccessListener(aVoid -> listener.onUserRoleChecked(false));
                }
            })
            .addOnFailureListener(e -> listener.onUserRoleChecked(false));
    }

    public void setAdminRole(String userId, boolean isAdmin, OnAdminRoleSetListener listener) {
        db.collection(COLLECTION_USERS)
            .document(userId)
            .update(FIELD_IS_ADMIN, isAdmin)
            .addOnSuccessListener(aVoid -> {
                Log.d("UserRepository", "Admin role set to " + isAdmin + " for user " + userId);
                listener.onAdminRoleSet(true);
            })
            .addOnFailureListener(e -> {
                Log.e("UserRepository", "Error setting admin role", e);
                listener.onAdminRoleSet(false);
            });
    }

    public interface OnUserRoleCheckedListener {
        void onUserRoleChecked(boolean isAdmin);
    }

    public interface OnAdminRoleSetListener {
        void onAdminRoleSet(boolean success);
    }
} 