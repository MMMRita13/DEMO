package com.example.diplom.ui.employees;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.databinding.FragmentEmployeesBinding;
import com.example.diplom.ui.employee.EmployeeDetailActivity;
import com.example.diplom.ui.employee.EmployeeEditActivity;
import com.example.diplom.ui.employees.adapter.EmployeeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EmployeesFragment extends Fragment {
    private static final String TAG = "EmployeesFragment";
    private FragmentEmployeesBinding binding;
    private EmployeesViewModel viewModel;
    private EmployeeAdapter adapter;
    private boolean isAdmin = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEmployeesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Проверяем авторизацию
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Log.e(TAG, "User is not authenticated");
            Toast.makeText(requireContext(), "Требуется авторизация", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверяем права администратора
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                isAdmin = documentSnapshot.getBoolean("isAdmin") != null && documentSnapshot.getBoolean("isAdmin");
                Log.d(TAG, "User admin status: " + isAdmin);
                // Показываем/скрываем FAB в зависимости от прав
                binding.fab.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error checking admin status", e);
                binding.fab.setVisibility(View.GONE);
            });

        viewModel = new ViewModelProvider(this).get(EmployeesViewModel.class);
        adapter = new EmployeeAdapter();

        // Подписываемся на изменения в ViewModel
        viewModel.getEmployees().observe(getViewLifecycleOwner(), employees -> {
            adapter.updateEmployees(employees);
        });

        adapter.setOnItemClickListener(employee -> {
            Log.d(TAG, "Employee clicked: " + employee.getName() + ", ID: " + employee.getId());
            try {
                Intent intent = new Intent(requireActivity(), EmployeeDetailActivity.class);
                intent.putExtra("employee_id", employee.getId());
                intent.putExtra("isAdmin", isAdmin);
                Log.d(TAG, "Starting EmployeeDetailActivity with ID: " + employee.getId());
                startActivity(intent);
                Log.d(TAG, "EmployeeDetailActivity started successfully");
            } catch (Exception e) {
                Log.e(TAG, "Error starting EmployeeDetailActivity", e);
                Toast.makeText(requireContext(), "Ошибка при открытии деталей сотрудника", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = binding.employeesRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        binding.searchView.setQueryHint("Поиск по ФИО, должности, отделу или статусу");
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchEmployees(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.searchEmployees(newText);
                return true;
            }
        });

        // Настраиваем FAB
        binding.fab.setOnClickListener(v -> {
            Log.d(TAG, "FAB clicked");
            Intent intent = new Intent(requireActivity(), EmployeeEditActivity.class);
            intent.putExtra("isAdmin", isAdmin);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.refreshEmployees();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 