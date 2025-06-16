package com.example.diplom.utils;

public class Validate {
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^[0-9]{11}$");
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() >= 2;
    }

    public static boolean isValidPosition(String position) {
        return position != null && !position.trim().isEmpty();
    }

    public static boolean isValidDepartment(String department) {
        return department != null && !department.trim().isEmpty();
    }
} 