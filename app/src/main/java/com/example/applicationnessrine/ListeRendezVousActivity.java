package com.example.applicationnessrine;

import androidx.appcompat.app.AppCompatActivity;

// ListeRendezVousActivity.java

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        // Créez l'adaptateur sans données initiales
        adapter = new AppointmentAdapter(this, new ArrayList<>());

        // Définissez l'adaptateur sur la ListView
        listViewRendezVous.setAdapter(adapter);

        // Chargez les rendez-vous depuis la base de données
        loadAppointmentsFromDatabase();
    }
    private void showOptionsDialog(long appointmentId) {
        // Pas besoin de rechercher l'objet Appointment ici
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");
        builder.setMessage("Que voulez-vous faire avec ce rendez-vous?");

        builder.setPositiveButton("Modifier", (dialog, which) -> {
            // Rediriger vers l'activité de mise à jour avec l'ID du rendez-vous
            AppointmentAdapter.openUpdateActivity(this, appointmentId);
        });

        builder.setNegativeButton("Supprimer", (dialog, which) -> {
            // Supprimer le rendez-vous de la base de données et actualiser la liste
            deleteAppointment(appointmentId);
        });

        builder.show();
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

    // Le reste de votre code...
}


