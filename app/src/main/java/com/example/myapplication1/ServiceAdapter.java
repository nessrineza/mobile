package com.example.myapplication1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.models.Offre;
import com.example.myapplication1.models.Service;
import com.example.myapplication1.services.MyDatabaseOperations;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private List<Service> serviceList;
    private MyDatabaseOperations databaseOperations;

    public ServiceAdapter(List<Service> serviceList, MyDatabaseOperations databaseOperations) {
        this.serviceList = serviceList;
        this.databaseOperations = databaseOperations;
    }


    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.bind(service);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView textViewNom;
        private final TextView listOfSubjects;
        private final ImageButton arrowButton;
        private final LinearLayout hiddenView;
        private final RecyclerView recyclerViewOffres;
        private final Button btnDelete;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            textViewNom = itemView.findViewById(R.id.textViewNom);
            listOfSubjects = itemView.findViewById(R.id.list_of_subjects);
            arrowButton = itemView.findViewById(R.id.arrow_button);
            hiddenView = itemView.findViewById(R.id.hidden_view);
            recyclerViewOffres = itemView.findViewById(R.id.recyclerViewOffres);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(Service service) {
            // Bind data to views
            textViewNom.setText(service.getNom());

            // Set up RecyclerView for offres
            List<Offre> offreList = service.getOffres();
            if (offreList != null) {
                OffreAdapter offreAdapter = new OffreAdapter(offreList);
                recyclerViewOffres.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                recyclerViewOffres.setAdapter(offreAdapter);
            }

            // Set click listener for expanding/collapsing
            arrowButton.setOnClickListener(v -> {
                if (hiddenView.getVisibility() == View.VISIBLE) {
                    hiddenView.setVisibility(View.GONE);
                    arrowButton.setImageResource(R.drawable.baseline_arrow_drop_down_24);
                } else {
                    hiddenView.setVisibility(View.VISIBLE);
                    arrowButton.setImageResource(R.drawable.baseline_arrow_drop_up_24);
                }
            });

            // Set click listener for delete button
            btnDelete.setOnClickListener(v -> {
                deleteService(getAdapterPosition());
            });
        }

        private void deleteService(int position) {
            serviceList.remove(position);
            notifyItemRemoved(position);
            // If needed, you can also delete the Service from the database
            // For this, you might need to pass a reference to your database helper or operations class
            // databaseHelper.deleteService(service);
        }
    }
}
