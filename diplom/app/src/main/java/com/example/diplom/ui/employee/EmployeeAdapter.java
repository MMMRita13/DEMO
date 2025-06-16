package com.example.diplom.ui.employee;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.data.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private static final String TAG = "EmployeeAdapter";
    private List<Employee> employees;
    private OnEmployeeClickListener listener;

    public interface OnEmployeeClickListener {
        void onEmployeeClick(Employee employee);
    }

    public EmployeeAdapter(List<Employee> employees, OnEmployeeClickListener listener) {
        this.employees = employees;
        this.listener = listener;
        Log.d(TAG, "Adapter created with " + (employees != null ? employees.size() : 0) + " employees");
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        Log.d(TAG, "Binding employee at position " + position + ": " + employee.getName());
        holder.bind(employee);
        
        // Добавляем обработчик нажатия на весь элемент
        holder.itemView.setOnClickListener(v -> {
            Log.d(TAG, "Item clicked at position " + position);
            if (listener != null) {
                Log.d(TAG, "Calling listener for employee: " + employee.getName());
                listener.onEmployeeClick(employee);
            } else {
                Log.e(TAG, "Listener is null!");
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees != null ? employees.size() : 0;
    }

    public void updateEmployees(List<Employee> employees) {
        Log.d(TAG, "Updating employees list, new size: " + (employees != null ? employees.size() : 0));
        this.employees = employees;
        notifyDataSetChanged();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private final TextView employeeName;
        private final TextView employeePosition;
        private final TextView employeeDepartment;
        private final TextView employeeStatus;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeName = itemView.findViewById(R.id.employeeName);
            employeePosition = itemView.findViewById(R.id.employeePosition);
            employeeDepartment = itemView.findViewById(R.id.employeeDepartment);
            employeeStatus = itemView.findViewById(R.id.employeeStatus);
        }

        public void bind(Employee employee) {
            employeeName.setText(employee.getName());
            employeePosition.setText(employee.getPosition());
            employeeDepartment.setText(employee.getDepartment());
            employeeStatus.setText(employee.getStatus() ? R.string.active : R.string.inactive);
            employeeStatus.setBackgroundResource(employee.getStatus() ? 
                android.R.color.holo_green_light : android.R.color.holo_red_light);
        }
    }
} 