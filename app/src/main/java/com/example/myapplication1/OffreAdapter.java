package com.example.myapplication1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.models.Offre;

import java.util.ArrayList;
import java.util.List;

public class OffreAdapter extends RecyclerView.Adapter<OffreAdapter.ViewHolder> {

    private List<Offre> offreList;
    private List<ViewHolder> viewHolders; // Maintain a list of ViewHolders

    public OffreAdapter(List<Offre> offreList) {
        this.offreList = offreList;
        this.viewHolders = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_offre, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolders.add(viewHolder); // Add ViewHolder to the list
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offre offre = offreList.get(position);

        // Set data to the views
        holder.editTextOffreNom.setText(offre.getNom());
        holder.editTextOffreDescription.setText(offre.getDescription());
    }

    @Override
    public int getItemCount() {
        return offreList.size();
    }

    // Method to add a new offer
    public void addOffre(Offre newOffre) {
        offreList.add(newOffre);

        // Notify the adapter that a new item has been added
        notifyDataSetChanged();
    }

    // Method to get the entire list of Offre
    public List<Offre> getOffreList() {
        return offreList;
    }



    // Method to add a new offer

    private void clearTextViewFields(ViewHolder viewHolder) {
        // Clear the TextView fields for the specified ViewHolder
        viewHolder.editTextOffreNom.setText("");
        viewHolder.editTextOffreDescription.setText("");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView editTextOffreNom;
        public TextView editTextOffreDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextOffreNom = itemView.findViewById(R.id.editTextOffreNom);
            editTextOffreDescription = itemView.findViewById(R.id.editTextOffreDescription);
        }
    }
}