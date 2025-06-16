package com.example.diplom.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.data.SafetyDocument;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SafetyDocumentAdapter extends RecyclerView.Adapter<SafetyDocumentAdapter.DocumentViewHolder> {
    private static final String TAG = "SafetyDocumentAdapter";
    private List<SafetyDocument> documents = new ArrayList<>();
    private final OnDocumentClickListener listener;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public interface OnDocumentClickListener {
        void onDocumentClick(SafetyDocument document);
    }

    public SafetyDocumentAdapter(OnDocumentClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_safety_document, parent, false);
            return new DocumentViewHolder(view);
        } catch (Exception e) {
            Log.e(TAG, "Error creating view holder", e);
            throw new RuntimeException("Error creating view holder", e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
        try {
            SafetyDocument document = documents.get(position);
            holder.bind(document);
        } catch (Exception e) {
            Log.e(TAG, "Error binding view holder", e);
        }
    }

    @Override
    public int getItemCount() {
        return documents != null ? documents.size() : 0;
    }

    public void setDocuments(List<SafetyDocument> documents) {
        try {
            this.documents = documents != null ? documents : new ArrayList<>();
            notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "Error setting documents", e);
            this.documents = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    class DocumentViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private final TextView categoryTextView;
        private final TextView dateTextView;

        DocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                titleTextView = itemView.findViewById(R.id.titleTextView);
                descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
                categoryTextView = itemView.findViewById(R.id.categoryTextView);
                dateTextView = itemView.findViewById(R.id.dateTextView);

                itemView.setOnClickListener(v -> {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && documents != null && position < documents.size()) {
                            listener.onDocumentClick(documents.get(position));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error handling click", e);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error initializing view holder", e);
                throw new RuntimeException("Error initializing view holder", e);
            }
        }

        void bind(SafetyDocument document) {
            try {
                if (document != null) {
                    titleTextView.setText(document.getTitle() != null ? document.getTitle() : "");
                    descriptionTextView.setText(document.getDescription() != null ? document.getDescription() : "");
                    categoryTextView.setText(document.getCategory() != null ? document.getCategory() : "");
                    if (document.getDate() != null) {
                        dateTextView.setText(dateFormat.format(document.getDate()));
                    } else {
                        dateTextView.setText("");
                    }
                } else {
                    titleTextView.setText("");
                    descriptionTextView.setText("");
                    categoryTextView.setText("");
                    dateTextView.setText("");
                }
            } catch (Exception e) {
                Log.e(TAG, "Error binding document", e);
                titleTextView.setText("");
                descriptionTextView.setText("");
                categoryTextView.setText("");
                dateTextView.setText("");
            }
        }
    }
} 