package com.example.diplom.util;

import android.content.Context;
import android.util.Log;

import com.example.diplom.model.Well;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PdfGenerator {
    private static final String TAG = "PdfGenerator";
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private static final Font SMALL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

    public static File generateWellReport(Context context, Well well) throws IOException, DocumentException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Заголовок
        Paragraph title = new Paragraph("Отчет по газовой скважине", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Дата генерации
        Paragraph date = new Paragraph("Дата генерации: " + 
            new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date()), 
            SMALL_FONT);
        date.setAlignment(Element.ALIGN_RIGHT);
        date.setSpacingAfter(20);
        document.add(date);

        // Основная информация
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Настройка ширины столбцов
        float[] columnWidths = {1f, 2f};
        table.setWidths(columnWidths);

        addTableRow(table, "Название скважины", well.getName());
        addTableRow(table, "Полное наименование", well.getFullName());
        addTableRow(table, "Местоположение", String.format("%.6f, %.6f", 
            well.getLocation().getLatitude(), well.getLocation().getLongitude()));
        addTableRow(table, "Глубина", String.format("%.2f м", well.getDepth()));
        addTableRow(table, "Плотность заполнения", String.format("%.2f г/см3", well.getProductionRate()));
        addTableRow(table, "Статус", well.getStatus());
        
        if (well.getLastInspection() != null) {
            addTableRow(table, "Последняя проверка", 
                new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(well.getLastInspection()));
        }

        document.add(table);

        // Анализ плотности заполнения
        if (well.getProductionRate() > 0) {
            Paragraph analysisTitle = new Paragraph("Анализ плотности заполнения", HEADER_FONT);
            analysisTitle.setSpacingBefore(20f);
            analysisTitle.setSpacingAfter(10f);
            document.add(analysisTitle);

            PdfPTable analysisTable = new PdfPTable(2);
            analysisTable.setWidthPercentage(100);
            analysisTable.setWidths(columnWidths);

            addTableRow(analysisTable, "Текущее значение", String.format("%.2f г/см3", well.getProductionRate()));
            addTableRow(analysisTable, "Рекомендуемый диапазон", "0.8 - 1.2 г/см3");
            addTableRow(analysisTable, "Статус", 
                well.getProductionRate() >= 0.8 && well.getProductionRate() <= 1.2 ? "Норма" : "Требует внимания");

            document.add(analysisTable);
        }

        // Рекомендации по проверке
        Paragraph recommendationsTitle = new Paragraph("Рекомендации по проверке", HEADER_FONT);
        recommendationsTitle.setSpacingBefore(20f);
        recommendationsTitle.setSpacingAfter(10f);
        document.add(recommendationsTitle);

        PdfPTable recommendationsTable = new PdfPTable(2);
        recommendationsTable.setWidthPercentage(100);
        recommendationsTable.setWidths(columnWidths);

        addTableRow(recommendationsTable, "Периодичность проверок", "Ежемесячно");
        addTableRow(recommendationsTable, "Следующая проверка", 
            new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(
                new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)));

        document.add(recommendationsTable);

        // Примечания
        if (well.getNotes() != null && !well.getNotes().isEmpty()) {
            Paragraph notesTitle = new Paragraph("Примечания", HEADER_FONT);
            notesTitle.setSpacingBefore(20f);
            notesTitle.setSpacingAfter(10f);
            document.add(notesTitle);

            Paragraph notes = new Paragraph(well.getNotes(), NORMAL_FONT);
            notes.setSpacingAfter(20f);
            document.add(notes);
        }

        document.close();
        return ReportUtils.saveReport(context, outputStream.toByteArray(), "well_report_" + well.getName(), "pdf");
    }

    private static void addTableRow(PdfPTable table, String label, String value) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, NORMAL_FONT));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(5f);
        labelCell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, NORMAL_FONT));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(5f);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }
} 