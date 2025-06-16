package com.example.diplom.util;

import android.content.Context;
import android.util.Log;

import com.example.diplom.model.Well;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExcelGenerator {
    private static final String TAG = "ExcelGenerator";

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static CellStyle createNormalStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static CellStyle createValueStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public static File generateTechnicalReport(Context context, Well well) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Технический отчет");

        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle normalStyle = createNormalStyle(workbook);
        CellStyle valueStyle = createValueStyle(workbook);

        // Заголовок
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Технический отчет по скважине");
        headerCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

        // Дата генерации
        Row dateRow = sheet.createRow(1);
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellValue("Дата генерации: " + new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date()));
        dateCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));

        // Основная информация
        int rowNum = 3;
        addRow(sheet, rowNum++, "Название скважины", well.getName(), normalStyle, valueStyle);
        addRow(sheet, rowNum++, "Полное наименование", well.getFullName(), normalStyle, valueStyle);
        addRow(sheet, rowNum++, "Глубина", String.format("%.2f м", well.getDepth()), normalStyle, valueStyle);
        addRow(sheet, rowNum++, "Координаты", String.format("%.6f, %.6f", 
            well.getLocation().getLatitude(), well.getLocation().getLongitude()), normalStyle, valueStyle);
        addRow(sheet, rowNum++, "Статус", well.getStatus(), normalStyle, valueStyle);
        addRow(sheet, rowNum++, "Плотность заполнения", String.format("%.2f г/см3", well.getProductionRate()), normalStyle, valueStyle);
        
        if (well.getLastInspection() != null) {
            addRow(sheet, rowNum++, "Последняя проверка", 
                new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(well.getLastInspection()), 
                normalStyle, valueStyle);
        }

        // Автоматическая настройка ширины столбцов
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        return saveWorkbook(context, workbook, "technical_report_" + well.getName());
    }

    public static File generateOperationalReport(Context context, Well well) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Эксплуатационный отчет");

        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle normalStyle = createNormalStyle(workbook);
        CellStyle valueStyle = createValueStyle(workbook);

        // Заголовок
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Эксплуатационный отчет по скважине");
        headerCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

        // Дата генерации
        Row dateRow = sheet.createRow(1);
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellValue("Дата генерации: " + new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date()));
        dateCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));

        // Эксплуатационные данные
        int rowNum = 3;
        addRow(sheet, rowNum++, "Название скважины", well.getName(), normalStyle, valueStyle);
        addRow(sheet, rowNum++, "Статус", well.getStatus(), normalStyle, valueStyle);
        addRow(sheet, rowNum++, "Плотность заполнения", String.format("%.2f г/см3", well.getProductionRate()), normalStyle, valueStyle);
        
        if (well.getLastInspection() != null) {
            addRow(sheet, rowNum++, "Последняя проверка", 
                new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(well.getLastInspection()), 
                normalStyle, valueStyle);
        }

        // Добавляем график плотности заполнения
        if (well.getProductionRate() > 0) {
            rowNum++;
            addRow(sheet, rowNum++, "Анализ плотности заполнения", "", normalStyle, valueStyle);
            addRow(sheet, rowNum++, "Текущее значение", String.format("%.2f г/см3", well.getProductionRate()), normalStyle, valueStyle);
            addRow(sheet, rowNum++, "Рекомендуемый диапазон", "0.8 - 1.2 г/см3", normalStyle, valueStyle);
            addRow(sheet, rowNum++, "Статус", 
                well.getProductionRate() >= 0.8 && well.getProductionRate() <= 1.2 ? "Норма" : "Требует внимания", 
                normalStyle, valueStyle);
        }

        // Автоматическая настройка ширины столбцов
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        return saveWorkbook(context, workbook, "operational_report_" + well.getName());
    }

    public static File generateInspectionReport(Context context, Well well) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отчет по проверкам");

        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle normalStyle = createNormalStyle(workbook);
        CellStyle valueStyle = createValueStyle(workbook);

        // Заголовок
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Отчет по проверкам скважины");
        headerCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

        // Дата генерации
        Row dateRow = sheet.createRow(1);
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellValue("Дата генерации: " + new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date()));
        dateCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));

        // Информация о проверках
        int rowNum = 3;
        addRow(sheet, rowNum++, "Название скважины", well.getName(), normalStyle, valueStyle);
        
        if (well.getLastInspection() != null) {
            addRow(sheet, rowNum++, "Последняя проверка", 
                new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(well.getLastInspection()), 
                normalStyle, valueStyle);
        }

        // Добавляем рекомендации по проверке
        rowNum += 2;
        addRow(sheet, rowNum++, "Рекомендации по проверке", "", normalStyle, valueStyle);
        addRow(sheet, rowNum++, "Периодичность проверок", "Ежемесячно", normalStyle, valueStyle);
        addRow(sheet, rowNum++, "Следующая проверка", 
            new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(
                new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)), 
            normalStyle, valueStyle);

        // Автоматическая настройка ширины столбцов
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        return saveWorkbook(context, workbook, "inspection_report_" + well.getName());
    }

    private static void addRow(Sheet sheet, int rowNum, String label, String value, 
                             CellStyle labelStyle, CellStyle valueStyle) {
        Row row = sheet.createRow(rowNum);
        Cell labelCell = row.createCell(0);
        Cell valueCell = row.createCell(1);
        
        labelCell.setCellValue(label);
        valueCell.setCellValue(value);
        
        labelCell.setCellStyle(labelStyle);
        valueCell.setCellStyle(valueStyle);
    }

    private static File saveWorkbook(Context context, Workbook workbook, String baseFileName) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
            return ReportUtils.saveReport(context, outputStream.toByteArray(), baseFileName, "xlsx");
        }
    }
} 