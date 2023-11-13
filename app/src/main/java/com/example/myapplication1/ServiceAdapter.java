package com.example.myapplication1;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        private final Button btnUpdate;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            textViewNom = itemView.findViewById(R.id.textViewNom);
            listOfSubjects = itemView.findViewById(R.id.list_of_subjects);
            arrowButton = itemView.findViewById(R.id.arrow_button);
            hiddenView = itemView.findViewById(R.id.hidden_view);
            recyclerViewOffres = itemView.findViewById(R.id.recyclerViewOffres);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);  // Initialize the button
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

            // Set click listener for update button
            btnUpdate.setOnClickListener(v -> {
                updateService(getAdapterPosition());
            });
        }

        private void deleteService(int position) {
            Service deletedService = serviceList.remove(position);
            notifyItemRemoved(position);

            // If needed, delete the Service from the database
            if (deletedService != null) {
                databaseOperations.open();
                int rowsAffected = databaseOperations.deleteService(deletedService);
                databaseOperations.close();

                if (rowsAffected > 0) {
                    // The service was successfully deleted from the database
                } else {
                    // Handle error if the service deletion from the database fails
                }
            }
        }

        private void updateService(int position) {
            Service selectedService = serviceList.get(position);

            // Create a dialog to get updated information for the service
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Update Service");

            // Inflate the dialog layout
            View dialogView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.dialog_update_service, null);
            builder.setView(dialogView);

            // Find views in the dialog layout
            EditText edtUpdateServiceName = dialogView.findViewById(R.id.edtUpdateServiceName);

            // Pre-fill the existing information
            edtUpdateServiceName.setText(selectedService.getNom());

            // Set up the dialog buttons
            builder.setPositiveButton("Update", (dialog, which) -> {
                // Get the updated information from the dialog
                String updatedServiceName = edtUpdateServiceName.getText().toString().trim();

                // Perform the update
                if (!updatedServiceName.isEmpty()) {
                    // Update the service name
                    selectedService.setNom(updatedServiceName);

                    // Notify the adapter that the data set has changed
                    notifyDataSetChanged();

                    // If needed, update the service in the database
                    databaseOperations.open();
                    int rowsAffected = databaseOperations.updateService(selectedService);
                    databaseOperations.close();

                    if (rowsAffected > 0) {
                        // The service was successfully updated in the database
                    } else {
                        // Handle error if the service update in the database fails
                    }
                } else {
                    // Handle the case where the updated information is empty
                    Toast.makeText(itemView.getContext(), "Service name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                // User canceled the update
                dialog.dismiss();
            });

            // Show the dialog
            builder.create().show();
        }
    }
}