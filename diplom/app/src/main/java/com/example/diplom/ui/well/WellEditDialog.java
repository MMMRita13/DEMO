package com.example.diplom.ui.well;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.diplom.R;
import com.example.diplom.data.Employee;
import com.example.diplom.data.EmployeeRepository;
import com.example.diplom.databinding.DialogWellBinding;
import com.example.diplom.model.Well;
import com.yandex.mapkit.geometry.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WellEditDialog extends DialogFragment {
    private static final String TAG = "WellEditDialog";
    private DialogWellBinding binding;
    private Well well;
    private OnWellSaveListener listener;
    private boolean isEditMode;
    private EmployeeRepository employeeRepository;
    private List<Employee> employees = new ArrayList<>();

    public interface OnWellSaveListener {
        void onWellSaved(Well well);
    }

    public WellEditDialog() {
        // Пустой конструктор для DialogFragment
    }

    public static WellEditDialog newInstance(@Nullable Well well, OnWellSaveListener listener) {
        WellEditDialog dialog = new WellEditDialog();
        dialog.well = well;
        dialog.listener = listener;
        dialog.isEditMode = well != null;
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_MaterialComponents_Dialog_MinWidth);
        employeeRepository = new EmployeeRepository();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogWellBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Устанавливаем заголовок
        if (isEditMode) {
            binding.dialogTitle.setText(R.string.edit_well);
        } else {
            binding.dialogTitle.setText(R.string.add_new_well);
        }

        // Инициализируем выпадающий список статусов
        String[] statuses = {
            getString(R.string.status_in_work),
            getString(R.string.status_not_in_work),
            getString(R.string.status_closed)
        };
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            statuses
        );
        binding.statusEditText.setAdapter(statusAdapter);

        // Загружаем список сотрудников
        loadEmployees();

        // Заполняем поля, если редактируем существующую скважину
        if (isEditMode && well != null) {
            binding.nameEditText.setText(well.getName());
            binding.fullNameEditText.setText(well.getFullName());
            binding.statusEditText.setText(well.getStatus(), false);
            binding.notesEditText.setText(well.getNotes());
            binding.depthEditText.setText(String.format("%.2f", well.getDepth()));
            binding.productionRateEditText.setText(String.format("%.2f", well.getProductionRate()));
            
            // Устанавливаем координаты
            if (well.getLocation() != null) {
                binding.latitudeEditText.setText(String.format("%.6f", well.getLocation().getLatitude()));
                binding.longitudeEditText.setText(String.format("%.6f", well.getLocation().getLongitude()));
            }
        }

        // Настраиваем кнопки
        binding.saveButton.setOnClickListener(v -> saveWell());
        binding.cancelButton.setOnClickListener(v -> dismiss());
    }

    private void loadEmployees() {
        employeeRepository.getEmployees(new EmployeeRepository.OnEmployeesLoadedListener() {
            @Override
            public void onEmployeesLoaded(List<Employee> loadedEmployees) {
                if (loadedEmployees != null) {
                    employees.clear();
                    employees.addAll(loadedEmployees);

                    List<String> employeeNames = new ArrayList<>();
                    for (Employee employee : employees) {
                        if (employee.getStatus()) {
                            employeeNames.add(employee.getName());
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        employeeNames
                    );
                    
                    binding.responsiblePersonSpinner.setAdapter(adapter);

                    if (isEditMode && well != null && well.getResponsiblePersonId() != null) {
                        for (Employee employee : employees) {
                            if (employee.getId().equals(well.getResponsiblePersonId())) {
                                binding.responsiblePersonSpinner.setText(employee.getName(), false);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private void saveWell() {
        Context context = getContext();
        if (context == null) {
            Log.e(TAG, "Context is null");
            return;
        }

        String name = binding.nameEditText.getText().toString().trim();
        String fullName = binding.fullNameEditText.getText().toString().trim();
        String status = binding.statusEditText.getText().toString().trim();
        String notes = binding.notesEditText.getText().toString().trim();
        String responsiblePersonName = binding.responsiblePersonSpinner.getText().toString().trim();

        if (name.isEmpty() || status.isEmpty() || fullName.isEmpty() || responsiblePersonName.isEmpty()) {
            Toast.makeText(context, R.string.fill_required_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        // Находим ID выбранного сотрудника
        String responsiblePersonId = null;
        Employee selectedEmployee = null;
        for (Employee employee : employees) {
            if (employee.getName().equals(responsiblePersonName)) {
                responsiblePersonId = employee.getId();
                selectedEmployee = employee;
                break;
            }
        }

        if (responsiblePersonId == null) {
            Toast.makeText(context, "Выберите ответственное лицо из списка", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверяем статус сотрудника
        if (selectedEmployee != null && !selectedEmployee.getStatus()) {
            Toast.makeText(context, "Нельзя назначить неактивного сотрудника ответственным лицом", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверяем количество назначений ответственного лица
        if (isEditMode && well != null) {
            if (!responsiblePersonId.equals(well.getResponsiblePersonId()) && well.getResponsiblePersonCount() >= 3) {
                Toast.makeText(context, "Достигнут лимит назначений ответственного лица (3 раза)", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Получаем координаты
        Point location = null;
        try {
            String latitudeStr = binding.latitudeEditText.getText().toString().trim().replace(',', '.');
            String longitudeStr = binding.longitudeEditText.getText().toString().trim().replace(',', '.');
            
            if (latitudeStr.isEmpty() || longitudeStr.isEmpty()) {
                Toast.makeText(context, "Введите координаты", Toast.LENGTH_SHORT).show();
                return;
            }
            
            double latitude = Double.parseDouble(latitudeStr);
            double longitude = Double.parseDouble(longitudeStr);
            
            if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
                Toast.makeText(context, "Введите корректные координаты", Toast.LENGTH_SHORT).show();
                return;
            }
            
            location = new Point(latitude, longitude);
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Введите корректные координаты", Toast.LENGTH_SHORT).show();
            return;
        }

        // Получаем числовые значения
        double depth = 0;
        double productionRate = 0;
        try {
            String depthStr = binding.depthEditText.getText().toString().trim().replace(',', '.');
            String productionRateStr = binding.productionRateEditText.getText().toString().trim().replace(',', '.');
            
            if (depthStr.isEmpty() || productionRateStr.isEmpty()) {
                Toast.makeText(context, "Введите все числовые значения", Toast.LENGTH_SHORT).show();
                return;
            }
            
            depth = Double.parseDouble(depthStr);
            productionRate = Double.parseDouble(productionRateStr);
            
            if (depth <= 0 || productionRate < 0) {
                Toast.makeText(context, "Введите корректные числовые значения", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Введите корректные числовые значения", Toast.LENGTH_SHORT).show();
            return;
        }

        // Создаем или обновляем скважину
        if (isEditMode && well != null) {
            well.setName(name);
            well.setFullName(fullName);
            if (!responsiblePersonId.equals(well.getResponsiblePersonId())) {
                well.incrementResponsiblePersonCount();
            }
            well.setResponsiblePersonId(responsiblePersonId);
            well.setStatus(status);
            well.setNotes(notes);
            well.setLocation(location);
            well.setDepth(depth);
            well.setProductionRate(productionRate);
            well.setLastInspection(new Date());
        } else {
            well = new Well(name, fullName, responsiblePersonId, location, depth, productionRate, 
                          status, new Date(), notes);
        }

        if (listener != null) {
            listener.onWellSaved(well);
        }
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 