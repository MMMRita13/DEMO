package com.example.diplom.ui.safety;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.data.SafetyDocument;
import com.example.diplom.data.SafetyDocumentRepository;
import com.example.diplom.ui.AddSafetyDocumentDialog;
import com.example.diplom.ui.SafetyDocumentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SafetyFragment extends Fragment implements SafetyDocumentAdapter.OnDocumentClickListener {
    private static final String TAG = "SafetyFragment";
    private RecyclerView recyclerView;
    private SafetyDocumentAdapter adapter;
    private SafetyDocumentRepository repository;
    private String currentCategory = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            repository = new SafetyDocumentRepository();
        } catch (Exception e) {
            Log.e(TAG, "Error initializing repository", e);
            Toast.makeText(getContext(), "Ошибка инициализации", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_safety_documents, container, false);

            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new SafetyDocumentAdapter(this);
            recyclerView.setAdapter(adapter);

            FloatingActionButton fab = view.findViewById(R.id.fab);
            fab.setOnClickListener(v -> showAddDocumentDialog());

            loadDocuments();

            return view;
        } catch (Exception e) {
            Log.e(TAG, "Error creating view", e);
            Toast.makeText(getContext(), "Ошибка создания интерфейса", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void loadDocuments() {
        try {
            if (currentCategory != null) {
                repository.getDocumentsByCategory(currentCategory, documents -> {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            try {
                                adapter.setDocuments(documents);
                            } catch (Exception e) {
                                Log.e(TAG, "Error setting documents", e);
                                Toast.makeText(getContext(), "Ошибка отображения документов", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            } else {
                repository.getDocuments(documents -> {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            try {
                                adapter.setDocuments(documents);
                            } catch (Exception e) {
                                Log.e(TAG, "Error setting documents", e);
                                Toast.makeText(getContext(), "Ошибка отображения документов", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading documents", e);
            Toast.makeText(getContext(), "Ошибка загрузки документов", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddDocumentDialog() {
        try {
            AddSafetyDocumentDialog dialog = new AddSafetyDocumentDialog(() -> {
                loadDocuments();
                Toast.makeText(getContext(), "Документ успешно добавлен", Toast.LENGTH_SHORT).show();
            });
            dialog.show(getParentFragmentManager(), "AddSafetyDocumentDialog");
        } catch (Exception e) {
            Log.e(TAG, "Error showing dialog", e);
            Toast.makeText(getContext(), "Ошибка открытия диалога", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDocumentClick(SafetyDocument document) {
        try {
            if (document != null && document.getFilePath() != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(document.getFilePath()), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Error opening document", e);
                    Toast.makeText(getContext(), "Не удалось открыть документ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Документ недоступен", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error handling document click", e);
            Toast.makeText(getContext(), "Ошибка при открытии документа", Toast.LENGTH_SHORT).show();
        }
    }

    public void setCategory(String category) {
        try {
            this.currentCategory = category;
            loadDocuments();
        } catch (Exception e) {
            Log.e(TAG, "Error setting category", e);
            Toast.makeText(getContext(), "Ошибка при фильтрации документов", Toast.LENGTH_SHORT).show();
        }
    }
} 