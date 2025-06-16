package com.example.diplom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.fragment.app.Fragment;

import com.example.diplom.databinding.ActivityMainBinding;
import com.example.diplom.ui.well.WellListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.diplom.ui.map.MapMarkerManager;
import com.example.diplom.data.WellRepository;
import com.example.diplom.model.Well;
import com.example.diplom.ui.map.MapFragment;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;

public class MainActivity extends AppCompatActivity implements MapFragment.OnWellActionListener {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private NavController navController;
    private boolean isAdmin;
    private AppBarConfiguration appBarConfiguration;
    private MapMarkerManager markerManager;
    private WellRepository wellRepository;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MapKitFactory.initialize(this);
        
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent receivedIntent = getIntent();
        isAdmin = receivedIntent.getBooleanExtra("isAdmin", false);

        try {
                        Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);


            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment);
            if (navHostFragment != null) {
                navController = navHostFragment.getNavController();

                appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_employees,
                    R.id.navigation_map,
                    R.id.navigation_safety
                ).build();

                // Настраиваем навигацию
                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                
                // Обновляем заголовок при навигации
                navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                    TextView toolbarTitle = findViewById(R.id.toolbar_title);
                    if (toolbarTitle != null) {
                        toolbarTitle.setText(destination.getLabel());
                    }
                });
                
                Log.d(TAG, "ActionBar setup completed");

                BottomNavigationView bottomNavigationView = binding.bottomNavigation;
                NavigationUI.setupWithNavController(bottomNavigationView, navController);
                Log.d(TAG, "BottomNavigationView setup completed");

                // Устанавливаем слушатель для MapFragment
                navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                    if (destination.getId() == R.id.navigation_map) {
                        // Получаем текущий фрагмент из NavHostFragment
                        Fragment currentFragment = navHostFragment.getChildFragmentManager()
                                .findFragmentById(R.id.nav_host_fragment);
                        if (currentFragment instanceof MapFragment) {
                            ((MapFragment) currentFragment).setOnWellActionListener(this);
                        }
                    }
                });
            } else {
                Log.e(TAG, "NavHostFragment is null");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing navigation", e);
        }

        // Инициализация репозитория
        wellRepository = new WellRepository();
    }

    @Override
    public void onEditWell(Well well) {
        Intent intent = new Intent(this, WellListActivity.class);
        intent.putExtra("isAdmin", isAdmin);
        intent.putExtra("wellId", well.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteWell(Well well) {
        Intent intent = new Intent(this, WellListActivity.class);
        intent.putExtra("isAdmin", isAdmin);
        intent.putExtra("wellId", well.getId());
        intent.putExtra("action", "delete");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu called");
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        Log.d(TAG, "Menu inflated successfully");
        Log.d(TAG, "Menu items count: " + menu.size());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected called for item: " + item.getItemId());
        if (item.getItemId() == R.id.action_wells) {
            Intent wellsIntent = new Intent(this, WellListActivity.class);
            wellsIntent.putExtra("isAdmin", isAdmin);
            startActivity(wellsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}