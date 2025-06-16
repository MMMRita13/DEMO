package com.example.diplom.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.diplom.R;
import com.example.diplom.model.Well;
import com.example.diplom.data.WellRepository;
import com.example.diplom.databinding.FragmentMapBinding;
import com.example.diplom.ui.well.WellInfoDialog;
import com.example.diplom.ui.well.WellEditDialog;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

import java.util.List;

public class MapFragment extends Fragment {
    private static final String TAG = "MapFragment";
    private FragmentMapBinding binding;
    private WellRepository wellRepository;
    private MapMarkerManager markerManager;
    private boolean isAdmin;
    private OnWellActionListener listener;

    public interface OnWellActionListener {
        void onEditWell(Well well);
        void onDeleteWell(Well well);
    }

    public void setOnWellActionListener(OnWellActionListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Получаем isAdmin из MainActivity
        if (getActivity() != null) {
            isAdmin = getActivity().getIntent().getBooleanExtra("isAdmin", false);
        }
        
        // Инициализация репозитория
        wellRepository = new WellRepository();
        
        // Инициализация менеджера меток
        markerManager = new MapMarkerManager(requireContext(), binding.mapview.getMap().getMapObjects());
        markerManager.setTapListener((mapObject, point) -> {
            if (mapObject.getUserData() instanceof Well) {
                Well well = (Well) mapObject.getUserData();
                WellInfoDialog dialog = WellInfoDialog.newInstance(well, new WellInfoDialog.OnWellActionListener() {
                    @Override
                    public void onEditWell(Well well) {
                        if (listener != null) {
                            listener.onEditWell(well);
                        } else {
                            // Если слушатель не установлен, показываем диалог редактирования прямо во фрагменте
                            WellEditDialog editDialog = WellEditDialog.newInstance(well, new WellEditDialog.OnWellSaveListener() {
                                @Override
                                public void onWellSaved(Well well) {
                                    wellRepository.saveWell(well, success -> {
                                        if (success) {
                                            Toast.makeText(requireContext(), "Скважина обновлена", Toast.LENGTH_SHORT).show();
                                            loadWells(); // Перезагружаем список скважин
                                        } else {
                                            Toast.makeText(requireContext(), "Ошибка при обновлении скважины", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            editDialog.show(getParentFragmentManager(), "EditWellDialog");
                        }
                    }

                    @Override
                    public void onGenerateReport(Well well) {
                        // TODO: Реализовать генерацию отчета
                        Toast.makeText(requireContext(), "Генерация отчета для скважины: " + well.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDeleteWell(Well well) {
                        wellRepository.deleteWell(well.getId(), success -> {
                            if (success) {
                                Toast.makeText(requireContext(), "Скважина удалена", Toast.LENGTH_SHORT).show();
                                loadWells(); // Перезагружаем список скважин
                            } else {
                                Toast.makeText(requireContext(), "Ошибка при удалении скважины", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.show(getParentFragmentManager(), "WellInfoDialog");
                return true;
            }
            return false;
        });

        // Устанавливаем начальную позицию карты
        binding.mapview.getMap().move(
            new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            new Animation(Animation.Type.SMOOTH, 0),
            null
        );

        loadWells();
    }

    private void loadWells() {
        wellRepository.getWells(new WellRepository.OnWellsLoadedListener() {
            @Override
            public void onWellsLoaded(List<Well> wells) {
                if (wells != null) {
            Log.d(TAG, "Loaded " + wells.size() + " wells");
                    markerManager.clearAll(); // Очищаем старые метки перед добавлением новых
            markerManager.addWells(wells);
                } else {
                    Log.e(TAG, "Failed to load wells: null response");
                    Toast.makeText(requireContext(), "Ошибка загрузки скважин", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        binding.mapview.onStart();
    }

    @Override
    public void onStop() {
        binding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 