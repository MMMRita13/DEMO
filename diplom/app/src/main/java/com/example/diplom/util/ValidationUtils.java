package com.example.diplom.util;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationUtils {
    
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    
    public static boolean isValidPhone(String phone) {
        // Удаляем все нецифровые символы
        String digitsOnly = phone.replaceAll("[^0-9]", "");
        // Проверяем, что длина равна 11 и начинается с 7 или 8
        return digitsOnly.length() == 11 && (digitsOnly.startsWith("7") || digitsOnly.startsWith("8"));
    }
    
    public static boolean isValidPassword(String password) {
        // Минимум 8 символов, хотя бы одна буква и одна цифра
        return password != null && password.length() >= 8 && 
               password.matches(".*[A-Za-z].*") && 
               password.matches(".*\\d.*");
    }
    
    public static boolean isValidName(String name) {
        // Имя должно содержать только буквы и пробелы, минимум 2 символа
        return name != null && name.length() >= 2 && 
               name.matches("[А-Яа-яЁё\\s-]+");
    }
    
    public static boolean isValidCoordinates(String latitude, String longitude) {
        try {
            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);
            return lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidDepth(String depth) {
        try {
            double value = Double.parseDouble(depth);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidProductionRate(String rate) {
        try {
            double value = Double.parseDouble(rate);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
} 