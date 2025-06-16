package com.example.diplom.ui.employees.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.data.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private static final String TAG = "EmployeeAdapter";
    private List<Employee> employees = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Employee employee);
    }

    public EmployeeAdapter() {
        Log.d(TAG, "Adapter created");
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        Log.d(TAG, "Setting listener: " + (listener != null ? "not null" : "null"));
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        Log.d(TAG, "Binding employee at position " + position + ": " + employee.getName());
        holder.bind(employee);
        
        holder.itemView.setOnClickListener(v -> {
            Log.d(TAG, "Item clicked at position " + position);
            if (listener != null) {
                Log.d(TAG, "Calling listener for employee: " + employee.getName());
                listener.onItemClick(employee);
            } else {
                Log.e(TAG, "Listener is null!");
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public void submitList(List<Employee> newList) {
        Log.d(TAG, "Submitting new list with " + (newList != null ? newList.size() : 0) + " items");
        this.employees = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void updateEmployees(List<Employee> newEmployees) {
        this.employees = newEmployees;
        notifyDataSetChanged();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private final TextView employeeName;
        private final TextView employeePosition;
        private final TextView employeeDepartment;
        private final TextView employeeStatus;

        EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeName = itemView.findViewById(R.id.employeeName);
            employeePosition = itemView.findViewById(R.id.employeePosition);
            employeeDepartment = itemView.findViewById(R.id.employeeDepartment);
            employeeStatus = itemView.findViewById(R.id.employeeStatus);
        }

        void bind(Employee employee) {
            employeeName.setText(employee.getName());
            employeePosition.setText(employee.getPosition());
            employeeDepartment.setText(employee.getDepartment());
            employeeStatus.setText(employee.getStatus() ? R.string.active : R.string.inactive);
            
            if (employee.getStatus()) {
                employeeStatus.setBackgroundResource(R.drawable.status_background_active);
                employeeStatus.setTextColor(itemView.getContext().getResources().getColor(android.R.color.white));
            } else {
                employeeStatus.setBackgroundResource(R.drawable.status_background);
                employeeStatus.setTextColor(itemView.getContext().getResources().getColor(android.R.color.white));
            }
        }
    }
} 