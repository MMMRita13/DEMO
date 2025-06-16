package com.example.diplom.ui.employees;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.diplom.data.Employee;
import com.example.diplom.data.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

public class EmployeesViewModel extends ViewModel {
    private final EmployeeRepository repository;
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>();
    private List<Employee> allEmployees = new ArrayList<>();

    public EmployeesViewModel() {
        repository = new EmployeeRepository();
        loadEmployees();
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    private void loadEmployees() {
        repository.getEmployees(new EmployeeRepository.OnEmployeesLoadedListener() {
            @Override
            public void onEmployeesLoaded(List<Employee> loadedEmployees) {
                allEmployees = loadedEmployees;
                employees.setValue(loadedEmployees);
            }
        });
    }

    public void searchEmployees(String query) {
        if (query == null || query.isEmpty()) {
            employees.setValue(allEmployees);
            return;
        }

        List<Employee> filteredList = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();
        
        for (Employee employee : allEmployees) {
            // Поиск по ФИО
            boolean matchesName = employee.getName().toLowerCase().contains(lowerCaseQuery);
            
            // Поиск по должности
            boolean matchesPosition = employee.getPosition().toLowerCase().contains(lowerCaseQuery);
            
            // Поиск по отделу
            boolean matchesDepartment = employee.getDepartment().toLowerCase().contains(lowerCaseQuery);
            
            // Поиск по статусу
            boolean matchesStatus = (employee.getStatus() && "активен".contains(lowerCaseQuery)) ||
                                  (!employee.getStatus() && "неактивен".contains(lowerCaseQuery));
            
            if (matchesName || matchesPosition || matchesDepartment || matchesStatus) {
                filteredList.add(employee);
            }
        }
        
        employees.setValue(filteredList);
    }

    public void refreshEmployees() {
        loadEmployees();
    }
} 