package com.example.diplom.ui.well;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.example.diplom.R;
import com.example.diplom.model.Well;
import com.example.diplom.util.ExcelGenerator;
import com.example.diplom.util.PdfGenerator;

import java.io.File;
import java.io.IOException;

public class ReportTypeDialog extends DialogFragment {
    private static final String TAG = "ReportTypeDialog";
    private Well well;
    private OnReportGeneratedListener listener;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;

    public interface OnReportGeneratedListener {
        void onReportGenerated(File reportFile);
    }

    public static ReportTypeDialog newInstance(Well well, OnReportGeneratedListener listener) {
        ReportTypeDialog dialog = new ReportTypeDialog();
        dialog.well = well;
        dialog.listener = listener;
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            permissions -> {
                boolean allGranted = true;
                for (Boolean isGranted : permissions.values()) {
                    if (!isGranted) {
                        allGranted = false;
                        break;
                    }
                }
                
                if (allGranted) {
                    generateReport(ReportType.TECHNICAL);
                } else {
                    Toast.makeText(requireContext(), 
                        "Для создания отчетов необходимы разрешения на доступ к хранилищу", 
                        Toast.LENGTH_LONG).show();
                }
            }
        );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        try {
            if (well == null) {
                Log.e(TAG, "Well is null");
                Toast.makeText(getContext(), "Ошибка: данные скважины недоступны", Toast.LENGTH_SHORT).show();
                dismiss();
                return super.onCreateDialog(savedInstanceState);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_report_type, null);

            view.findViewById(R.id.technicalReportButton).setOnClickListener(v -> checkPermissionsAndGenerate(ReportType.TECHNICAL));
            view.findViewById(R.id.operationalReportButton).setOnClickListener(v -> checkPermissionsAndGenerate(ReportType.OPERATIONAL));
            view.findViewById(R.id.inspectionReportButton).setOnClickListener(v -> checkPermissionsAndGenerate(ReportType.INSPECTION));

            builder.setView(view)
                    .setNegativeButton("Отмена", (dialog, which) -> dismiss());

            return builder.create();
        } catch (Exception e) {
            Log.e(TAG, "Error creating dialog", e);
            Toast.makeText(getContext(), "Ошибка создания диалога", Toast.LENGTH_SHORT).show();
            return super.onCreateDialog(savedInstanceState);
        }
    }

    private void checkPermissionsAndGenerate(ReportType type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);
                    Toast.makeText(requireContext(), 
                        "Пожалуйста, предоставьте разрешение на доступ ко всем файлам", 
                        Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e(TAG, "Error requesting MANAGE_EXTERNAL_STORAGE permission", e);
                    Toast.makeText(requireContext(), 
                        "Не удалось запросить разрешение на доступ к файлам", 
                        Toast.LENGTH_LONG).show();
                }
                return;
            }
        } else {
            String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            
            boolean allGranted = true;
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(requireContext(), permission) 
                    != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            
            if (!allGranted) {
                requestPermissionLauncher.launch(permissions);
                return;
            }
        }
        
        generateReport(type);
    }

    private void generateReport(ReportType type) {
        try {
            if (well == null) {
                Log.e(TAG, "Well is null in generateReport");
                Toast.makeText(requireContext(), "Ошибка: данные скважины недоступны", Toast.LENGTH_SHORT).show();
                dismiss();
                return;
            }

            File reportFile;
            String mimeType;
            switch (type) {
                case TECHNICAL:
                    reportFile = ExcelGenerator.generateTechnicalReport(requireContext(), well);
                    mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                    break;
                case OPERATIONAL:
                    reportFile = ExcelGenerator.generateOperationalReport(requireContext(), well);
                    mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                    break;
                case INSPECTION:
                    reportFile = PdfGenerator.generateWellReport(requireContext(), well);
                    mimeType = "application/pdf";
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный тип отчета");
            }

            if (reportFile != null && reportFile.exists()) {
                if (listener != null) {
                    listener.onReportGenerated(reportFile);
                }
                
                openReportFile(reportFile);
            } else {
                Log.e(TAG, "Report file is null or doesn't exist");
                Toast.makeText(requireContext(), "Ошибка при создании отчета", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error generating report", e);
            Toast.makeText(requireContext(), "Ошибка при создании отчета: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error", e);
            Toast.makeText(requireContext(), "Неожиданная ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            dismiss();
        }
    }

    private void openReportFile(File reportFile) {
        try {
            Log.d(TAG, "Открытие файла: " + reportFile.getAbsolutePath());
            
            // Получаем URI через FileProvider
            Uri fileUri = FileProvider.getUriForFile(
                requireContext(),
                requireContext().getPackageName() + ".provider",
                reportFile
            );
            Log.d(TAG, "URI файла: " + fileUri);

            // Создаем Intent для просмотра файла
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, getMimeType(reportFile.getName()));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Предоставляем разрешение на чтение всем приложениям
            requireContext().grantUriPermission(
                requireContext().getPackageName(),
                fileUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            );

            // Запускаем просмотр файла
            startActivity(intent);
            Log.d(TAG, "Файл успешно открыт");
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "Не найдено приложение для открытия файла", e);
            Toast.makeText(requireContext(), 
                "Не найдено приложение для открытия файла", 
                Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при открытии файла", e);
            Toast.makeText(requireContext(), 
                "Ошибка при открытии файла: " + e.getMessage(), 
                Toast.LENGTH_LONG).show();
        }
    }

    private String getMimeType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "pdf":
                return "application/pdf";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "xls":
                return "application/vnd.ms-excel";
            default:
                return "application/octet-stream";
        }
    }

    private enum ReportType {
        TECHNICAL,
        OPERATIONAL,
        INSPECTION
    }
} 