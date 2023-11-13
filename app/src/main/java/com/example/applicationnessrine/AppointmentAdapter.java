package com.example.applicationnessrine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.applicationnessrine.model.Appointment;

import java.util.List;

public class AppointmentAdapter extends ArrayAdapter<Appointment> {
    public AppointmentAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    // ... Autres méthodes et variables
    public AppointmentAdapter(Context context, List<Appointment> appointments) {
        super(context, 0, appointments);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_appointment, parent, false);
        }

        // Obtenez l'objet Appointment à cette position
        Appointment appointment = getItem(position);

        // Remplissez les vues avec les données de rendez-vous
        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewDate = convertView.findViewById(R.id.textViewDate);
        Button updateButton = convertView.findViewById(R.id.updateButton);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        if (appointment != null) {
            textViewName.setText(appointment.getTitle());
            textViewDate.setText(appointment.getDate());

            // Ajoutez ici les écouteurs d'événements pour les boutons si nécessaire
            // Par exemple, vous pouvez ajouter un OnClickListener pour chaque bouton
            updateButton.setOnClickListener(v -> {
                // Ouvrir l'activité de mise à jour lorsque le bouton de mise à jour est cliqué
                openUpdateActivity(getContext(), appointment.getId());
            });

            deleteButton.setOnClickListener(v -> {
                // Afficher la boîte de dialogue de confirmation de suppression
                showDeleteConfirmationDialog(getContext(), appointment.getId());
            });


    }
        return convertView;
       }
    public static void openUpdateActivity(Context context, long appointmentId) {
        Intent intent = new Intent(context, UpdateAppointmentActivity.class);
        intent.putExtra("APPOINTMENT_ID", appointmentId);
        context.startActivity(intent);
    }

    public static void showDeleteConfirmationDialog(Context context, long appointmentId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Voulez-vous vraiment supprimer ce rendez-vous?");
        builder.setPositiveButton("Oui", (dialog, which) -> {
            deleteAppointment(context, appointmentId);
        });
        builder.setNegativeButton("Non", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }
    private void loadAppointmentsFromDatabase() {
        DataSource dataSource = new DataSource(getContext());
        dataSource.open();
        List<Appointment> appointments = dataSource.getAppointments();
        dataSource.close();

        clear();
        addAll(appointments);
        notifyDataSetChanged();
    }
    public static void deleteAppointment(Context context, long appointmentId) {
        DataSource dataSource = new DataSource(context);
        dataSource.open();
        dataSource.deleteAppointment(appointmentId);
        dataSource.close();
    }


    // ... Autres méthodes et classes nécessaires
}

