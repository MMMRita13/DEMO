package com.example.diplom.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.diplom.R;
import com.example.diplom.data.Employee;
import com.example.diplom.data.EmployeeRepository;
import com.example.diplom.databinding.ActivityEmployeeDetailBinding;

import java.util.List;

public class EmployeeDetailActivity extends AppCompatActivity {
    private static final String TAG = "EmployeeDetailActivity";
    
    private ActivityEmployeeDetailBinding binding;
    private EmployeeRepository employeeRepository;
    private Employee employee;
    private boolean isAdmin;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Получаем данные из Intent
        String employeeId = getIntent().getStringExtra("employee_id");
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        Log.d(TAG, "isAdmin: " + isAdmin);
        
        // Инициализация репозитория
        employeeRepository = new EmployeeRepository();
        
        // Настройка Toolbar
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.employee_details);
        }
        
        // Загрузка данных сотрудника
        loadEmployee(employeeId);
    }
    
    private void loadEmployee(String employeeId) {
        employeeRepository.getEmployees(new EmployeeRepository.OnEmployeesLoadedListener() {
            @Override
            public void onEmployeesLoaded(List<Employee> employees) {
                for (Employee e : employees) {
                    if (e.getId().equals(employeeId)) {
                        employee = e;
                        updateUI();
                        break;
                    }
                }
            }
        });
    }
    
    private void updateUI() {
        if (employee != null) {
            binding.nameTextView.setText(employee.getName());
            binding.positionTextView.setText(employee.getPosition());
            binding.departmentTextView.setText(employee.getDepartment());
            binding.emailTextView.setText(employee.getEmail());
            binding.phoneTextView.setText(employee.getPhone());
            binding.statusTextView.setText(employee.getStatus() ? R.string.active : R.string.inactive);
            
            // Устанавливаем цвет фона в зависимости от статуса
            if (employee.getStatus()) {
                binding.statusTextView.setBackgroundResource(R.drawable.status_background_active);
            } else {
                binding.statusTextView.setBackgroundResource(R.drawable.status_background);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isAdmin) {
            getMenuInflater().inflate(R.menu.employee_detail_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_edit) {
            editEmployee();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            deleteEmployee();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editEmployee() {
        Intent intent = new Intent(this, EmployeeEditActivity.class);
        intent.putExtra("employee_id", employee.getId());
        intent.putExtra("isAdmin", isAdmin);
        startActivity(intent);
    }

    private void deleteEmployee() {
        new AlertDialog.Builder(this)
            .setTitle(R.string.delete_employee)
            .setMessage(R.string.delete_employee_confirmation)
            .setPositiveButton(R.string.delete, (dialog, which) -> {
                employeeRepository.deleteEmployee(employee.getId(), new EmployeeRepository.OnEmployeeOperationListener() {
                    @Override
                    public void onEmployeeOperationSuccess() {
                        Toast.makeText(EmployeeDetailActivity.this, R.string.employee_deleted, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onEmployeeOperationFailure(String error) {
                        Toast.makeText(EmployeeDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            })
            .setNegativeButton(R.string.cancel, null)
            .show();
    }
} 