package com.example.diplom.data;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class SafetyDocumentRepository {
    private static final String TAG = "SafetyDocumentRepository";
    private static final String COLLECTION_NAME = "safety_documents";
    private final FirebaseFirestore db;

    public interface OnDocumentsLoadedListener {
        void onDocumentsLoaded(List<SafetyDocument> documents);
    }

    public SafetyDocumentRepository() {
        try {
            db = FirebaseFirestore.getInstance();
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Firestore", e);
            throw new RuntimeException("Failed to initialize Firestore", e);
        }
    }

    public void getDocuments(OnDocumentsLoadedListener listener) {
        try {
            db.collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    try {
                        List<SafetyDocument> documents = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            try {
                                SafetyDocument safetyDocument = document.toObject(SafetyDocument.class);
                                if (safetyDocument != null) {
                                    safetyDocument.setId(document.getId());
                                    documents.add(safetyDocument);
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "Error converting document: " + document.getId(), e);
                            }
                        }
                        listener.onDocumentsLoaded(documents);
                    } catch (Exception e) {
                        Log.e(TAG, "Error processing documents", e);
                        listener.onDocumentsLoaded(new ArrayList<>());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error loading documents", e);
                    listener.onDocumentsLoaded(new ArrayList<>());
                });
        } catch (Exception e) {
            Log.e(TAG, "Error in getDocuments", e);
            listener.onDocumentsLoaded(new ArrayList<>());
        }
    }

    public void getDocumentsByCategory(String category, OnDocumentsLoadedListener listener) {
        if (category == null) {
            listener.onDocumentsLoaded(new ArrayList<>());
            return;
        }

        try {
            db.collection(COLLECTION_NAME)
                .whereEqualTo("category", category)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    try {
                        List<SafetyDocument> documents = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            try {
                                SafetyDocument safetyDocument = document.toObject(SafetyDocument.class);
                                if (safetyDocument != null) {
                                    safetyDocument.setId(document.getId());
                                    documents.add(safetyDocument);
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "Error converting document: " + document.getId(), e);
                            }
                        }
                        listener.onDocumentsLoaded(documents);
                    } catch (Exception e) {
                        Log.e(TAG, "Error processing documents by category", e);
                        listener.onDocumentsLoaded(new ArrayList<>());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error loading documents by category", e);
                    listener.onDocumentsLoaded(new ArrayList<>());
                });
        } catch (Exception e) {
            Log.e(TAG, "Error in getDocumentsByCategory", e);
            listener.onDocumentsLoaded(new ArrayList<>());
        }
    }

    public void saveDocument(SafetyDocument document, OnDocumentSavedListener listener) {
        if (document == null) {
            listener.onDocumentSaved(false);
            return;
        }

        try {
            if (document.getId() == null) {
                db.collection(COLLECTION_NAME)
                    .add(document)
                    .addOnSuccessListener(documentReference -> {
                        try {
                            document.setId(documentReference.getId());
                            listener.onDocumentSaved(true);
                        } catch (Exception e) {
                            Log.e(TAG, "Error setting document ID", e);
                            listener.onDocumentSaved(false);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error saving document", e);
                        listener.onDocumentSaved(false);
                    });
            } else {
                db.collection(COLLECTION_NAME)
                    .document(document.getId())
                    .set(document)
                    .addOnSuccessListener(aVoid -> listener.onDocumentSaved(true))
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error updating document", e);
                        listener.onDocumentSaved(false);
                    });
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in saveDocument", e);
            listener.onDocumentSaved(false);
        }
    }

    public void deleteDocument(String documentId, OnDocumentDeletedListener listener) {
        if (documentId == null) {
            listener.onDocumentDeleted(false);
            return;
        }

        try {
            db.collection(COLLECTION_NAME)
                .document(documentId)
                .delete()
                .addOnSuccessListener(aVoid -> listener.onDocumentDeleted(true))
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error deleting document", e);
                    listener.onDocumentDeleted(false);
                });
        } catch (Exception e) {
            Log.e(TAG, "Error in deleteDocument", e);
            listener.onDocumentDeleted(false);
        }
    }

    public interface OnDocumentSavedListener {
        void onDocumentSaved(boolean success);
    }

    public interface OnDocumentDeletedListener {
        void onDocumentDeleted(boolean success);
    }
} 