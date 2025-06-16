package com.example.diplom.ui.well;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.databinding.ItemWellBinding;
import com.example.diplom.model.Well;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WellAdapter extends RecyclerView.Adapter<WellAdapter.WellViewHolder> {
    private final List<Well> wells;
    private final OnWellClickListener listener;

    public interface OnWellClickListener {
        void onWellClick(Well well);
    }

    public WellAdapter(OnWellClickListener listener) {
        this.listener = listener;
        this.wells = new ArrayList<>();
    }

    @NonNull
    @Override
    public WellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWellBinding binding = ItemWellBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false);
        return new WellViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull WellViewHolder holder, int position) {
        holder.bind(wells.get(position));
    }

    @Override
    public int getItemCount() {
        return wells.size();
    }

    public void updateWells(List<Well> newWells) {
        wells.clear();
        wells.addAll(newWells);
        notifyDataSetChanged();
    }

    public void addWell(Well well) {
        int position = wells.size();
        wells.add(well);
        notifyItemInserted(position);
    }

    public void updateWell(Well well) {
        int position = findWellPosition(well.getId());
        if (position != -1) {
            wells.set(position, well);
            notifyItemChanged(position);
        }
    }

    public void removeWell(Well well) {
        int position = findWellPosition(well.getId());
        if (position != -1) {
            wells.remove(position);
            notifyItemRemoved(position);
        }
    }

    private int findWellPosition(String wellId) {
        for (int i = 0; i < wells.size(); i++) {
            if (wells.get(i).getId().equals(wellId)) {
                return i;
            }
        }
        return -1;
    }

    static class WellViewHolder extends RecyclerView.ViewHolder {
        private final ItemWellBinding binding;
        private final OnWellClickListener listener;

        WellViewHolder(ItemWellBinding binding, OnWellClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        void bind(Well well) {
            binding.wellName.setText(well.getName());
            binding.wellStatus.setText(well.getStatus());
            
            if (well.getLocation() != null) {
                String locationText = String.format("%.6f, %.6f", 
                    well.getLocation().getLatitude(), 
                    well.getLocation().getLongitude());
                Log.d("WellAdapter", "Setting location text: " + locationText);
                binding.wellLocation.setText(locationText);
            } else {
                Log.d("WellAdapter", "Location is null for well: " + well.getName());
                binding.wellLocation.setText(itemView.getContext().getString(R.string.no_location));
            }

            binding.wellDepth.setText(String.format("%.2f м", well.getDepth()));
            binding.wellProductionRate.setText(String.format("%.2f г/см3", well.getProductionRate()));

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onWellClick(well);
                }
            });
        }
    }
} 