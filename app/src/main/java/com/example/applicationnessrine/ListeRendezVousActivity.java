package com.example.applicationnessrine;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.applicationnessrine.AppointmentAdapter.UpdateClickListener;


import com.example.applicationnessrine.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class ListeRendezVousActivity extends AppCompatActivity {

    private ListView listViewRendezVous;
    private AppointmentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rendez_vous);

        listViewRendezVous = findViewById(R.id.listViewRendezVous);

        // Create the adapter with an empty list
        adapter = new AppointmentAdapter(this, new ArrayList<>());

        // Set the update click listener for the adapter
        adapter.setUpdateClickListener(appointmentId -> {
            // Handle the update click event by opening UpdateAppointmentActivity
            Intent intent = new Intent(this, UpdateAppointmentActivity.class);
            intent.putExtra("APPOINTMENT_ID", appointmentId);
            startActivity(intent);
        });

        // Set the adapter for the ListView
        listViewRendezVous.setAdapter(adapter);

        // Load appointments from the database
        loadAppointmentsFromDatabase();

        listViewRendezVous.setOnItemClickListener((parent, view, position, id) -> {
            long appointmentId = adapter.getItemId(position);
            showOptionsDialog(appointmentId);
        });
    }

    private void showOptionsDialog(long appointmentId) {
        // Fetch the appointment by ID
        DataSource dataSource = new DataSource(this);
        dataSource.open();
        Appointment appointment = dataSource.getAppointmentById(appointmentId);
        dataSource.close();

        if (appointment == null) {
            Log.e("ListeRendezVousActivity", "Appointment not found for ID: " + appointmentId);
            // Handle the case where the appointment is not found
            return;
        }

        // Build the options dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");
        builder.setMessage("Que voulez-vous faire avec ce rendez-vous?");

        builder.setPositiveButton("Modifier", (dialog, which) -> {
            Log.d("ListeRendezVousActivity", "Modifier button clicked for appointment ID: " + appointmentId);
            // Redirect to the update activity with the appointment ID
            if (appointmentId != -1) {
                Intent intent = new Intent(this, UpdateAppointmentActivity.class);
                intent.putExtra("APPOINTMENT_ID", appointmentId);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Supprimer", (dialog, which) -> {
            // Delete the appointment from the database and refresh the list
            deleteAppointment(appointmentId);
        });

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteAppointment(long appointmentId) {
        // Supprimer le rendez-vous de la base de données
        DataSource dataSource = new DataSource(this);
        dataSource.open();
        dataSource.deleteAppointment(appointmentId);
        dataSource.close();

        // Actualiser la liste des rendez-vous après la suppression
        loadAppointmentsFromDatabase();
    }

    private void loadAppointmentsFromDatabase() {
        DataSource dataSource = new DataSource(this);
        dataSource.open();
        List<Appointment> appointments = dataSource.getAppointments();
        dataSource.close();

        adapter.clear();
        adapter.addAll(appointments);
        adapter.notifyDataSetChanged();
    }
}

