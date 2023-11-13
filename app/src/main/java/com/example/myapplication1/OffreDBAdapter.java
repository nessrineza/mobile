package com.example.myapplication1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.models.Offre;

import java.util.List;

public class OffreDBAdapter extends RecyclerView.Adapter<OffreDBAdapter.OffreDBViewHolder> {

    private List<Offre> offreDBList;

    public OffreDBAdapter(List<Offre> offreDBList) {
        this.offreDBList = offreDBList;
    }

    @NonNull
    @Override
    public OffreDBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_offre_db, parent, false);
        return new OffreDBViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffreDBViewHolder holder, int position) {
        if (offreDBList != null) {
            Offre offreDB = offreDBList.get(position);
            holder.bind(offreDB);
        }
    }

    @Override
    public int getItemCount() {
        return offreDBList == null ? 0 : offreDBList.size();
    }

    static class OffreDBViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewOffre;

        public OffreDBViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOffre = itemView.findViewById(R.id.textViewOffreNomDB);
        }

        public void bind(Offre offreDB) {
            // Bind data to views
            textViewOffre.setText(offreDB.getNom());
        }
    }
}