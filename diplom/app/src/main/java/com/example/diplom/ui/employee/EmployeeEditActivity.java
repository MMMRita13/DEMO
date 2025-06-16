package com.example.diplom.ui.employee;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diplom.R;
import com.example.diplom.data.Employee;
import com.example.diplom.data.EmployeeRepository;
import com.example.diplom.databinding.ActivityEmployeeEditBinding;
import com.example.diplom.utils.Validate;

import java.util.List;

public class EmployeeEditActivity extends AppCompatActivity {
    private ActivityEmployeeEditBinding binding;
    private EmployeeRepository employeeRepository;
    private boolean isAdmin;
    private String employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        if (!isAdmin) {
            Toast.makeText(this, R.string.admin_only, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.add_employee);
        }

        employeeId = getIntent().getStringExtra("employee_id");
        employeeRepository = new EmployeeRepository();

        if (employeeId != null) {
            loadEmployee(employeeId);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.edit_employee);
            }
        }

        setupSaveButton();
    }

    private void loadEmployee(String employeeId) {
        employeeRepository.getEmployees(new EmployeeRepository.OnEmployeesLoadedListener() {
            @Override
            public void onEmployeesLoaded(List<Employee> employees) {
                for (Employee employee : employees) {
                    if (employee.getId().equals(employeeId)) {
                        populateForm(employee);
                        break;
                    }
                }
            }
        });
    }

    private void populateForm(Employee employee) {
        binding.nameEditText.setText(employee.getName());
        binding.positionEditText.setText(employee.getPosition());
        binding.departmentEditText.setText(employee.getDepartment());
        binding.emailEditText.setText(employee.getEmail());
        binding.phoneEditText.setText(employee.getPhone());
        binding.statusSwitch.setChecked(employee.getStatus());
    }

    private void setupSaveButton() {
        binding.saveButton.setOnClickListener(v -> saveEmployee());
    }

    private void saveEmployee() {
        if (!isAdmin) {
            Toast.makeText(this, R.string.admin_only, Toast.LENGTH_SHORT).show();
            return;
        }

        String name = binding.nameEditText.getText().toString().trim();
        String position = binding.positionEditText.getText().toString().trim();
        String department = binding.departmentEditText.getText().toString().trim();
        String email = binding.emailEditText.getText().toString().trim();
        String phone = binding.phoneEditText.getText().toString().trim();
        boolean status = binding.statusSwitch.isChecked();

        if (!Validate.isValidName(name)) {
            Toast.makeText(this, R.string.invalid_name, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validate.isValidPosition(position)) {
            Toast.makeText(this, R.string.invalid_position, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validate.isValidDepartment(department)) {
            Toast.makeText(this, R.string.invalid_department, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validate.isValidEmail(email)) {
            Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validate.isValidPhone(phone)) {
            Toast.makeText(this, R.string.invalid_phone, Toast.LENGTH_SHORT).show();
            return;
        }

        Employee employee = new Employee(name, position, department, email, phone, status);
        
        if (employeeId != null) {
            employee.setId(employeeId);
            employeeRepository.updateEmployee(employee, new EmployeeRepository.OnEmployeeOperationListener() {
                @Override
                public void onEmployeeOperationSuccess() {
                    Toast.makeText(EmployeeEditActivity.this, R.string.employee_updated, Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onEmployeeOperationFailure(String error) {
                    Toast.makeText(EmployeeEditActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            employeeRepository.addEmployee(employee, new EmployeeRepository.OnEmployeeOperationListener() {
                @Override
                public void onEmployeeOperationSuccess() {
                    Toast.makeText(EmployeeEditActivity.this, R.string.employee_added, Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onEmployeeOperationFailure(String error) {
                    Toast.makeText(EmployeeEditActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 