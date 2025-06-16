package com.example.diplom.data;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRepository {
    private static final String TAG = "EmployeeRepository";
    private static final String COLLECTION_EMPLOYEES = "employees";
    
    private final FirebaseFirestore db;

    public EmployeeRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void getEmployees(OnEmployeesLoadedListener listener) {
        Log.d(TAG, "Getting employees from Firestore...");
        db.collection(COLLECTION_EMPLOYEES)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                List<Employee> employees = new ArrayList<>();
                Log.d(TAG, "Successfully got " + queryDocumentSnapshots.size() + " documents");
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    try {
                        Employee employee = document.toObject(Employee.class);
                        employee.setId(document.getId());
                        employees.add(employee);
                        Log.d(TAG, "Added employee: " + employee.getName() + " with ID: " + employee.getId());
                    } catch (Exception e) {
                        Log.e(TAG, "Error converting document to Employee", e);
                    }
                }
                Log.d(TAG, "Calling listener with " + employees.size() + " employees");
                listener.onEmployeesLoaded(employees);
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error loading employees", e);
                listener.onEmployeesLoaded(new ArrayList<>());
            });
    }

    public void addEmployee(Employee employee, OnEmployeeOperationListener listener) {
        Map<String, Object> employeeData = new HashMap<>();
        employeeData.put("name", employee.getName());
        employeeData.put("position", employee.getPosition());
        employeeData.put("department", employee.getDepartment());
        employeeData.put("email", employee.getEmail());
        employeeData.put("phone", employee.getPhone());
        employeeData.put("status", employee.getStatus());

        Log.d(TAG, "Adding employee: " + employee.getName());
        db.collection(COLLECTION_EMPLOYEES)
            .add(employeeData)
            .addOnSuccessListener(documentReference -> {
                Log.d(TAG, "Employee added with ID: " + documentReference.getId());
                listener.onEmployeeOperationSuccess();
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error adding employee", e);
                listener.onEmployeeOperationFailure(e.getMessage());
            });
    }

    public void updateEmployee(Employee employee, OnEmployeeOperationListener listener) {
        if (employee.getId() == null) {
            listener.onEmployeeOperationFailure("Employee ID is null");
            return;
        }

        Map<String, Object> employeeData = new HashMap<>();
        employeeData.put("name", employee.getName());
        employeeData.put("position", employee.getPosition());
        employeeData.put("department", employee.getDepartment());
        employeeData.put("email", employee.getEmail());
        employeeData.put("phone", employee.getPhone());
        employeeData.put("status", employee.getStatus());

        Log.d(TAG, "Updating employee: " + employee.getName() + " with ID: " + employee.getId());
        db.collection(COLLECTION_EMPLOYEES)
            .document(employee.getId())
            .update(employeeData)
            .addOnSuccessListener(aVoid -> {
                Log.d(TAG, "Employee updated successfully");
                listener.onEmployeeOperationSuccess();
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error updating employee", e);
                listener.onEmployeeOperationFailure(e.getMessage());
            });
    }

    public void deleteEmployee(String employeeId, OnEmployeeOperationListener listener) {
        Log.d(TAG, "Deleting employee with ID: " + employeeId);
        db.collection(COLLECTION_EMPLOYEES)
            .document(employeeId)
            .delete()
            .addOnSuccessListener(aVoid -> {
                Log.d(TAG, "Employee deleted successfully");
                listener.onEmployeeOperationSuccess();
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error deleting employee", e);
                listener.onEmployeeOperationFailure(e.getMessage());
            });
    }

    public interface OnEmployeesLoadedListener {
        void onEmployeesLoaded(List<Employee> employees);
    }

    public interface OnEmployeeOperationListener {
        void onEmployeeOperationSuccess();
        void onEmployeeOperationFailure(String error);
    }
} 