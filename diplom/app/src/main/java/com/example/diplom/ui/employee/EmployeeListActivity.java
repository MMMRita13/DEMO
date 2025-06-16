package com.example.diplom.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diplom.R;
import com.example.diplom.data.Employee;
import com.example.diplom.data.EmployeeRepository;
import com.example.diplom.databinding.ActivityEmployeeListBinding;
import com.example.diplom.ui.employees.adapter.EmployeeAdapter;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity {
    private static final String TAG = "EmployeeListActivity";
    private ActivityEmployeeListBinding binding;
    private EmployeeRepository employeeRepository;
    private EmployeeAdapter adapter;
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.employees);
        }

        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        Log.d(TAG, "isAdmin: " + isAdmin);

        employeeRepository = new EmployeeRepository();
        setupRecyclerView();
        setupFab();

        loadEmployees();
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new EmployeeAdapter();
        Log.d(TAG, "Adapter created");
        
        adapter.setOnItemClickListener(employee -> {
            Log.d(TAG, "Employee clicked: " + employee.getName() + ", ID: " + employee.getId());
            try {
                Intent intent = new Intent(EmployeeListActivity.this, EmployeeDetailActivity.class);
                intent.putExtra("employee_id", employee.getId());
                intent.putExtra("isAdmin", isAdmin);
                Log.d(TAG, "Starting EmployeeDetailActivity with ID: " + employee.getId());
                startActivity(intent);
                Log.d(TAG, "EmployeeDetailActivity started successfully");
            } catch (Exception e) {
                Log.e(TAG, "Error starting EmployeeDetailActivity", e);
                Toast.makeText(EmployeeListActivity.this, "Ошибка при открытии деталей сотрудника", Toast.LENGTH_SHORT).show();
            }
        });
        
        binding.recyclerView.setAdapter(adapter);
        Log.d(TAG, "Adapter set to RecyclerView");
    }

    private void setupFab() {
        Log.d(TAG, "Setting up FAB");
        binding.fab.setVisibility(View.VISIBLE);
        binding.fab.setOnClickListener(v -> {
            Log.d(TAG, "FAB clicked");
            Intent intent = new Intent(this, EmployeeEditActivity.class);
            intent.putExtra("isAdmin", isAdmin);
            startActivity(intent);
        });
    }

    private void loadEmployees() {
        Log.d(TAG, "Loading employees...");
        employeeRepository.getEmployees(new EmployeeRepository.OnEmployeesLoadedListener() {
            @Override
            public void onEmployeesLoaded(List<Employee> employees) {
                Log.d(TAG, "Loaded " + (employees != null ? employees.size() : 0) + " employees");
                if (employees != null && !employees.isEmpty()) {
                    Log.d(TAG, "First employee: " + employees.get(0).getName());
                    adapter.submitList(employees);
                    Log.d(TAG, "Employees updated in adapter");
                } else {
                    Log.d(TAG, "No employees found");
                    Toast.makeText(EmployeeListActivity.this, 
                        R.string.employee_found, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_refresh) {
            loadEmployees();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEmployees();
    }
} 