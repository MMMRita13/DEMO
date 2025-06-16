package com.example.diplom.ui.safety;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.diplom.R;
import com.example.diplom.data.SafetyDocument;
import com.example.diplom.data.SafetyDocumentRepository;

import java.util.Date;

public class AddSafetyDocumentDialog extends DialogFragment {
    private static final String TAG = "AddSafetyDocumentDialog";
    private static final int PICK_PDF_FILE = 1;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private AutoCompleteTextView categoryAutoComplete;
    private Button selectFileButton;
    private Uri selectedFileUri;
    private final SafetyDocumentRepository repository;

    public interface OnDocumentAddedListener {
        void onDocumentAdded();
    }

    private OnDocumentAddedListener listener;

    public AddSafetyDocumentDialog(OnDocumentAddedListener listener) {
        this.listener = listener;
        this.repository = new SafetyDocumentRepository();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_add_safety_document, null);

            titleEditText = view.findViewById(R.id.titleEditText);
            descriptionEditText = view.findViewById(R.id.descriptionEditText);
            categoryAutoComplete = view.findViewById(R.id.categoryAutoComplete);
            selectFileButton = view.findViewById(R.id.selectFileButton);

            // Настройка выпадающего списка категорий
            String[] categories = {"Инструкции", "Правила", "Протоколы", "Другое"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_dropdown_item_1line, categories);
            categoryAutoComplete.setAdapter(adapter);

            selectFileButton.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("application/pdf");
                    startActivityForResult(intent, PICK_PDF_FILE);
                } catch (Exception e) {
                    Log.e(TAG, "Error opening file picker", e);
                    Toast.makeText(getContext(), "Ошибка при выборе файла", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setView(view)
                    .setTitle("Добавить документ")
                    .setPositiveButton("Сохранить", null)
                    .setNegativeButton("Отмена", (dialog, which) -> dismiss());

            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(dialogInterface -> {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(v -> saveDocument());
            });

            return dialog;
        } catch (Exception e) {
            Log.e(TAG, "Error creating dialog", e);
            Toast.makeText(getContext(), "Ошибка создания диалога", Toast.LENGTH_SHORT).show();
            return super.onCreateDialog(savedInstanceState);
        }
    }

    private void saveDocument() {
        try {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String category = categoryAutoComplete.getText().toString().trim();

            if (title.isEmpty()) {
                titleEditText.setError("Введите название");
                return;
            }

            if (category.isEmpty()) {
                categoryAutoComplete.setError("Выберите категорию");
                return;
            }

            if (selectedFileUri == null) {
                Toast.makeText(getContext(), "Выберите файл", Toast.LENGTH_SHORT).show();
                return;
            }

            SafetyDocument document = new SafetyDocument();
            document.setTitle(title);
            document.setDescription(description);
            document.setCategory(category);
            document.setFilePath(selectedFileUri.toString());
            document.setDate(new Date());

            repository.saveDocument(document, success -> {
                if (success) {
                    if (listener != null) {
                        listener.onDocumentAdded();
                    }
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Ошибка при сохранении документа", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error saving document", e);
            Toast.makeText(getContext(), "Ошибка при сохранении документа", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_PDF_FILE && data != null) {
                selectedFileUri = data.getData();
                selectFileButton.setText("Файл выбран");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error handling activity result", e);
            Toast.makeText(getContext(), "Ошибка при выборе файла", Toast.LENGTH_SHORT).show();
        }
    }
} 