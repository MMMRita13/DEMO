package com.example.diplom.ui.well;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.diplom.R;
import com.example.diplom.data.Employee;
import com.example.diplom.data.EmployeeRepository;
import com.example.diplom.model.Well;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.io.File;

public class WellInfoDialog extends DialogFragment {
    private static final String TAG = "WellInfoDialog";
    private Well well;
    private OnWellActionListener listener;
    private EmployeeRepository employeeRepository;

    public interface OnWellActionListener {
        void onEditWell(Well well);
        void onGenerateReport(Well well);
        void onDeleteWell(Well well);
    }

    public WellInfoDialog() {
        // Пустой конструктор для DialogFragment
    }

    public static WellInfoDialog newInstance(Well well, OnWellActionListener listener) {
        WellInfoDialog dialog = new WellInfoDialog();
        dialog.well = well;
        dialog.listener = listener;
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        employeeRepository = new EmployeeRepository();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_well_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (well == null) {
            dismiss();
            return;
        }

        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView fullNameTextView = view.findViewById(R.id.fullNameTextView);
        TextView responsiblePersonTextView = view.findViewById(R.id.responsiblePersonTextView);
        TextView coordinatesTextView = view.findViewById(R.id.coordinatesTextView);
        TextView depthTextView = view.findViewById(R.id.depthTextView);
        TextView productionRateLabel = view.findViewById(R.id.productionRateLabel);
        ImageView productionRateArrow = view.findViewById(R.id.productionRateArrow);
        TextView statusTextView = view.findViewById(R.id.statusTextView);
        TextView lastInspectionTextView = view.findViewById(R.id.lastInspectionTextView);
        TextView notesTextView = view.findViewById(R.id.notesTextView);
        Button editButton = view.findViewById(R.id.editButton);
        Button generateReportButton = view.findViewById(R.id.btnGenerateReport);
        Button deleteButton = view.findViewById(R.id.btnDeleteWell);

        nameTextView.setText(well.getName());
        fullNameTextView.setText("Полное наименование: " + well.getFullName());
        
        // Загружаем информацию об ответственном лице
        if (well.getResponsiblePersonId() != null) {
            employeeRepository.getEmployees(new EmployeeRepository.OnEmployeesLoadedListener() {
                @Override
                public void onEmployeesLoaded(List<Employee> employees) {
                    for (Employee employee : employees) {
                        if (employee.getId().equals(well.getResponsiblePersonId())) {
                            responsiblePersonTextView.setText("Ответственное лицо: " + employee.getName());
                            break;
                        }
                    }
                }
            });
        } else {
            responsiblePersonTextView.setText("Ответственное лицо: не назначено");
        }
        
        if (well.getLocation() != null) {
        coordinatesTextView.setText(String.format(Locale.getDefault(), "%.6f, %.6f", 
            well.getLocation().getLatitude(), well.getLocation().getLongitude()));
        } else {
            coordinatesTextView.setText("Координаты: не указаны");
        }

        depthTextView.setText(String.format("Глубина: %.2f м", well.getDepth()));
        
        // Отображение плотности заполнения с индикатором
        double productionRate = well.getProductionRate();
        productionRateLabel.setText(String.format("Плотность заполнения скважины: %.2f г/см3", productionRate));
        
        if (productionRate > 1.0) {
            productionRateArrow.setImageResource(R.drawable.ic_arrow_up_green);
            productionRateArrow.setVisibility(View.VISIBLE);
        } else if (productionRate < 1.0) {
            productionRateArrow.setImageResource(R.drawable.ic_arrow_down_red);
            productionRateArrow.setVisibility(View.VISIBLE);
        } else {
            productionRateArrow.setVisibility(View.GONE);
        }
        
        statusTextView.setText("Статус: " + well.getStatus());
        
        if (well.getLastInspection() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            lastInspectionTextView.setText("Последняя проверка: " + dateFormat.format(well.getLastInspection()));
        } else {
            lastInspectionTextView.setText("Последняя проверка: не указана");
        }
        
        notesTextView.setText("Примечания: " + (well.getNotes() != null ? well.getNotes() : ""));

        // Настраиваем кнопки
        editButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditWell(well);
            }
            dismiss();
        });

        generateReportButton.setOnClickListener(v -> {
            ReportTypeDialog reportTypeDialog = ReportTypeDialog.newInstance(well, new ReportTypeDialog.OnReportGeneratedListener() {
                @Override
                public void onReportGenerated(File reportFile) {
            if (listener != null) {
                listener.onGenerateReport(well);
            }
                    Toast.makeText(requireContext(), "Отчет сохранен: " + reportFile.getName(), Toast.LENGTH_LONG).show();
                }
            });
            reportTypeDialog.show(getParentFragmentManager(), "ReportTypeDialog");
        });

        deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.delete_well)
                    .setMessage(R.string.delete_well_confirmation)
                    .setPositiveButton(R.string.delete, (dialog, which) -> {
                        listener.onDeleteWell(well);
                        dismiss();
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
            }
        });

        // Показываем/скрываем кнопки в зависимости от прав администратора
        boolean isAdmin = getActivity() != null && getActivity().getIntent().getBooleanExtra("isAdmin", false);
        deleteButton.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
    }
} 