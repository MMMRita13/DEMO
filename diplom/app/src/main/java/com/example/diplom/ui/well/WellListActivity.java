package com.example.diplom.ui.well;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diplom.R;
import com.example.diplom.model.Well;
import com.example.diplom.data.WellRepository;
import com.example.diplom.databinding.ActivityWellListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class WellListActivity extends AppCompatActivity implements 
    WellAdapter.OnWellClickListener,
    WellInfoDialog.OnWellActionListener,
    WellEditDialog.OnWellSaveListener {

    private static final String TAG = "WellListActivity";
    private ActivityWellListBinding binding;
    private WellRepository wellRepository;
    private WellAdapter adapter;
    private FirebaseFirestore db;
    private boolean isAdmin;
    private List<Well> allWells = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWellListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       wellRepository = new WellRepository();

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.wells);
        }

        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        Log.d(TAG, "isAdmin: " + isAdmin);

        db = FirebaseFirestore.getInstance();

        setupRecyclerView();
        setupFab();
        setupSearchView();
        setupStatusFilter();

        loadWells();

        String wellId = getIntent().getStringExtra("wellId");
        String action = getIntent().getStringExtra("action");
        if (wellId != null && !wellId.isEmpty()) {
            Log.d(TAG, "Opening well with ID: " + wellId);
            wellRepository.getWellById(wellId, well -> {
                if (well != null) {
                    if ("delete".equals(action)) {
                        showDeleteConfirmationDialog(well);
                    } else {
                        showEditWellDialog(well);
                    }
                } else {
                    Log.e(TAG, "Well not found with ID: " + wellId);
                    Toast.makeText(this, "Скважина не найдена", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WellAdapter(this);
        binding.recyclerView.setAdapter(adapter);
    }

    private void setupFab() {
        binding.fabAddWell.setVisibility(View.VISIBLE);
        binding.fabAddWell.setOnClickListener(v -> showAddWellDialog());
    }

    private void setupSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "Search submitted: " + query);
                performSearch(query, binding.statusFilter.getText().toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "Search text changed: " + newText);
                performSearch(newText, binding.statusFilter.getText().toString());
                return true;
            }
        });
    }

    private void setupStatusFilter() {
        List<String> statuses = new ArrayList<>();
        statuses.add("Все"); // Пустой статус для отображения всех скважин
        statuses.add("В работе");
        statuses.add("Не в работе");
        statuses.add("Закрыта");

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            statuses
        );

        binding.statusFilter.setAdapter(statusAdapter);
        binding.statusFilter.setOnItemClickListener((parent, view, position, id) -> {
            String selectedStatus = (String) parent.getItemAtPosition(position);
            String currentQuery = binding.searchView.getQuery() != null ? 
                                binding.searchView.getQuery().toString() : "";
            Log.d(TAG, "Status filter selected: " + selectedStatus);
            performSearch(currentQuery, selectedStatus);
        });
    }

    private void performSearch(String nameQuery, String statusQuery) {
        Log.d(TAG, "Performing search with name: '" + nameQuery + "', status: '" + statusQuery + "'");
        Log.d(TAG, "Total wells before filtering: " + allWells.size());

        // Логируем все скважины и их статусы перед фильтрацией
        for (Well well : allWells) {
            Log.d(TAG, "Well before filtering: " + well.getName() + 
                      ", Status: '" + well.getStatus() + "'" +
                      ", Status length: " + (well.getStatus() != null ? well.getStatus().length() : "null"));
        }

        // Если оба фильтра пустые, показываем все скважины
        if ((nameQuery == null || nameQuery.trim().isEmpty()) && 
            (statusQuery == null || statusQuery.equals("Все"))) {
            Log.d(TAG, "No filters applied, showing all wells");
            adapter.updateWells(allWells);
            return;
        }

        nameQuery = nameQuery != null ? nameQuery.trim().toLowerCase() : "";
        statusQuery = statusQuery != null ? statusQuery.trim() : "";
        
        Log.d(TAG, "Filtering with status: '" + statusQuery + "'" +
                  ", Status length: " + statusQuery.length());
        
        List<Well> filteredWells = new ArrayList<>();
        for (Well well : allWells) {
            boolean nameMatch = nameQuery.isEmpty() || 
                              well.getName().toLowerCase().contains(nameQuery);
            
            boolean statusMatch;
            String wellStatus = well.getStatus() != null ? well.getStatus().trim() : "";
            
            Log.d(TAG, "Checking well: " + well.getName() + 
                      ", Well status: '" + wellStatus + "'" +
                      ", Filter status: '" + statusQuery + "'");
            
            if (statusQuery.equals("Все")) {
                statusMatch = true;
            } else if (statusQuery.equalsIgnoreCase("В работе")) {
                statusMatch = wellStatus.equalsIgnoreCase("в работе");
                Log.d(TAG, "Checking 'В работе' - Match: " + statusMatch);
            } else if (statusQuery.equalsIgnoreCase("Не в работе")) {
                statusMatch = !wellStatus.equalsIgnoreCase("в работе");
                Log.d(TAG, "Checking 'Не в работе' - Match: " + statusMatch);
            } else {
                statusMatch = wellStatus.equalsIgnoreCase(statusQuery);
                Log.d(TAG, "Checking exact match - Match: " + statusMatch);
            }
            
            if (nameMatch && statusMatch) {
                filteredWells.add(well);
                Log.d(TAG, "Well matched: " + well.getName() + 
                          " (status: '" + wellStatus + "')");
            }
        }
        
        Log.d(TAG, "Filtered wells count: " + filteredWells.size());
        adapter.updateWells(filteredWells);
    }

    private void loadWells() {
        wellRepository.getWells(new WellRepository.OnWellsLoadedListener() {
            @Override
            public void onWellsLoaded(List<Well> wells) {
                if (wells != null) {
                allWells.clear();
                    allWells.addAll(wells);
                    adapter.updateWells(wells);
                } else {
                    Toast.makeText(WellListActivity.this, "Ошибка при загрузке скважин", Toast.LENGTH_SHORT).show();
                }
            }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.well_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_refresh) {
            loadWells();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWellClick(Well well) {
        Log.d(TAG, "Well clicked: " + well.getName());
        showWellInfoDialog(well);
    }

    private void showWellInfoDialog(Well well) {
        Log.d(TAG, "Showing info dialog for well: " + well.getName());
        WellInfoDialog dialog = WellInfoDialog.newInstance(well, this);
        dialog.show(getSupportFragmentManager(), "WellInfoDialog");
    }

    private void showAddWellDialog() {
        WellEditDialog dialog = WellEditDialog.newInstance(null, this);
        dialog.show(getSupportFragmentManager(), "AddWellDialog");
    }

    private void showEditWellDialog(Well well) {
        Log.d(TAG, "Showing edit dialog for well: " + well.getName());
        WellEditDialog dialog = WellEditDialog.newInstance(well, this);
        dialog.show(getSupportFragmentManager(), "EditWellDialog");
    }

    @Override
    public void onEditWell(Well well) {
            showEditWellDialog(well);
    }

    @Override
    public void onGenerateReport(Well well) {
        Toast.makeText(this, "Генерация отчета для скважины: " + well.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWellSaved(Well well) {
        Log.d(TAG, "Saving well: " + well.getName());
        wellRepository.saveWell(well, success -> {
            if (success) {
        if (well.getId() == null) {
                    adapter.addWell(well);
                    allWells.add(well);
                    Toast.makeText(this, R.string.well_added, Toast.LENGTH_SHORT).show();
        } else {
                    adapter.updateWell(well);
                    // Обновляем скважину в списке allWells
                    for (int i = 0; i < allWells.size(); i++) {
                        if (allWells.get(i).getId().equals(well.getId())) {
                            allWells.set(i, well);
                            break;
                        }
                    }
                    Toast.makeText(this, R.string.well_updated, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Ошибка при сохранении скважины", Toast.LENGTH_SHORT).show();
            }
                });
    }

    @Override
    public void onDeleteWell(Well well) {
        wellRepository.deleteWell(well.getId(), success -> {
            if (success) {
                adapter.removeWell(well);
                allWells.remove(well);
                Toast.makeText(this, R.string.well_deleted, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.error_deleting_well, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog(Well well) {
        new AlertDialog.Builder(this)
            .setTitle(R.string.delete_well)
            .setMessage(R.string.delete_well_confirmation)
            .setPositiveButton(R.string.delete, (dialog, which) -> {
                wellRepository.deleteWell(well.getId(), success -> {
                    if (success) {
                        adapter.removeWell(well);
                        allWells.remove(well);
                        Toast.makeText(this, R.string.well_deleted, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, R.string.error_deleting_well, Toast.LENGTH_SHORT).show();
                    }
                });
            })
            .setNegativeButton(R.string.cancel, null)
            .show();
    }
} 