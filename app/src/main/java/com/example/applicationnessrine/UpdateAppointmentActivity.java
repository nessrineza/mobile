package com.example.applicationnessrine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationnessrine.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class UpdateAppointmentActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextLocation;
    private EditText editTextDate;
    private EditText editTextTime;
    private long appointmentId;
    private List<Appointment> appointments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_appointment);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);

        Intent intent = getIntent();
        if (intent.hasExtra("APPOINTMENT_ID")) {
            appointmentId = intent.getLongExtra("APPOINTMENT_ID", -1);
            // Récupérez les détails du rendez-vous à partir de la base de données
            loadAppointmentDetails();
        } else {
            // Gérer l'erreur
            finish();
        }

        // ... Autres initialisations et écouteurs d'événements



    // Ajoutez un écouteur sur le bouton "Sauvegarder les modifications"
        Button btnSaveUpdate = findViewById(R.id.buttonSaveUpdate);
        btnSaveUpdate.setOnClickListener(v -> {
            // Appel de la méthode pour sauvegarder les modifications
            updateAppointment();
        });
    }

    private void loadAppointmentDetails() {
        // Assurez-vous que la liste d'appointments n'est pas vide
        if (!appointments.isEmpty()) {
            // Obtenez l'objet Appointment à partir de la liste en fonction de l'ID
            Appointment appointment = findAppointmentById(appointmentId);

            // Remplissez les champs avec les détails du rendez-vous
            if (appointment != null) {
                editTextTitle.setText(appointment.getTitle());
                editTextLocation.setText(appointment.getLocation());
                editTextDate.setText(appointment.getDate());
                editTextTime.setText(appointment.getTime());
            }
        }
    }

    // Méthode pour trouver un rendez-vous dans la liste en fonction de l'ID
    private Appointment findAppointmentById(long id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId() == id) {
                return appointment;
            }
        }
        return null; // Rendez-vous non trouvé
    }



    private void updateAppointment() {
        // Récupérez les nouvelles valeurs des champs
        String newTitle = editTextTitle.getText().toString();
        String newLocation = editTextLocation.getText().toString();
        String newDate = editTextDate.getText().toString();
        String newTime = editTextTime.getText().toString();

        // Créez un objet Appointment avec les nouvelles valeurs
        Appointment updatedAppointment = new Appointment(appointmentId, newTitle, newLocation, newDate, newTime);

        // Mettez à jour la base de données
        DataSource dataSource = new DataSource(this);
        dataSource.open();
        dataSource.updateAppointmentById(appointmentId,newTitle,newLocation,newDate,newTime);
        dataSource.close();

        // Terminez l'activité ou effectuez d'autres actions nécessaires
        finish();
    }
}
