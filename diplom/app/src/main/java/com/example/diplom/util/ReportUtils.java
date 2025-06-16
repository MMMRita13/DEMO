package com.example.diplom.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReportUtils {
    private static final String TAG = "ReportUtils";
    private static final String REPORTS_DIR = "GeoStrazh_Reports";

    public static File saveReport(Context context, byte[] data, String baseFileName, String extension) throws IOException {
        Log.d(TAG, "Начало сохранения отчета: " + baseFileName);
        
        // Проверяем доступность хранилища
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "Внешнее хранилище недоступно");
            throw new IOException("Внешнее хранилище недоступно");
        }

        // Создаем директорию для отчетов
        File reportsDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), REPORTS_DIR);
        Log.d(TAG, "Путь к директории отчетов: " + reportsDir.getAbsolutePath());
        
        if (!reportsDir.exists()) {
            Log.d(TAG, "Создание директории для отчетов");
            if (!reportsDir.mkdirs()) {
                Log.e(TAG, "Не удалось создать директорию для отчетов");
                throw new IOException("Не удалось создать директорию для отчетов");
            }
        }

        // Формируем имя файла
        String fileName = String.format("%s_%s.%s",
            baseFileName.replaceAll("\\s+", "_"),
            new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()),
            extension);
        Log.d(TAG, "Имя файла: " + fileName);

        // Создаем файл
        File reportFile = new File(reportsDir, fileName);
        Log.d(TAG, "Полный путь к файлу: " + reportFile.getAbsolutePath());

        try {
            // Проверяем возможность записи
            if (!reportsDir.canWrite()) {
                Log.e(TAG, "Нет прав на запись в директорию");
                throw new IOException("Нет прав на запись в директорию");
            }

            // Записываем файл
            try (FileOutputStream outputStream = new FileOutputStream(reportFile)) {
                Log.d(TAG, "Начало записи файла");
                outputStream.write(data);
                Log.d(TAG, "Файл успешно записан");
            }
        } catch (IOException e) {
            Log.e(TAG, "Ошибка при записи файла", e);
            throw e;
        }

        // Проверяем, что файл создан
        if (!reportFile.exists()) {
            Log.e(TAG, "Файл не был создан");
            throw new IOException("Файл не был создан");
        }

        Log.d(TAG, "Файл успешно создан: " + reportFile.getAbsolutePath());
        return reportFile;
    }
} 