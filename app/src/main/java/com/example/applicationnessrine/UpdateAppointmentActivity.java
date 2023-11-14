package com.example.applicationnessrine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_appointment);

        // ... Other initializations

        Intent intent = getIntent();
        if (intent.hasExtra("APPOINTMENT_ID")) {
            appointmentId = getIntent().getLongExtra("APPOINTMENT_ID", -1);

            // Missing call to pre-fill the edit fields with the appointment details
            loadAppointmentDetails(appointmentId);

            // Initialize the list of appointments (add this line)
            DataSource dataSource = new DataSource(this);
            dataSource.open();
            List<Appointment> appointments = dataSource.getAppointments();
            dataSource.close();
        } else {
            // Handle the error
            finish();
        }

        // ... Add the listener for the "Save Changes" button
        Button btnSaveUpdate = findViewById(R.id.buttonSaveUpdate);
        btnSaveUpdate.setOnClickListener(v -> {
            // Call the method to save the changes
            updateAppointment();
        });
    }

    private void loadAppointmentDetails(long appointmentId) {
        // Retrieve the appointment details from the database
        DataSource dataSource = new DataSource(this);
        dataSource.open();
        Appointment appointment = dataSource.getAppointmentById(appointmentId);
        dataSource.close();

        if (appointment != null) {
            editTextTitle = findViewById(R.id.editTextTitle);
            editTextLocation = findViewById(R.id.editTextLocation);
            editTextDate = findViewById(R.id.editTextDate);
            editTextTime = findViewById(R.id.editTextTime);

            // Set the existing appointment details to the EditText fields
            editTextTitle.setText(appointment.getTitle());
            editTextLocation.setText(appointment.getLocation());
            editTextDate.setText(appointment.getDate());
            editTextTime.setText(appointment.getTime());
        } else {
            Log.e("UpdateAppointment", "No appointment found with ID: " + appointmentId);
        }
    }

    private void updateAppointment() {
        // Retrieve the new values from the fields
        String newTitle = editTextTitle.getText().toString().trim();
        String newLocation = editTextLocation.getText().toString().trim();
        String newDate = editTextDate.getText().toString().trim();
        String newTime = editTextTime.getText().toString().trim();

        // Check if the appointmentId is valid
        if (appointmentId != -1) {
            // Update the appointment in the database
            DataSource dataSource = new DataSource(this);
            dataSource.open();
            int rowsUpdated = dataSource.updateAppointmentById(appointmentId, newTitle, newLocation, newDate, newTime);
            dataSource.close();

            if (rowsUpdated > 0) {
                Log.d("UpdateAppointment", "Appointment updated successfully. Rows affected: " + rowsUpdated);

                // Navigate back to the ListeRendezVousActivity or perform other actions
                Intent intent = new Intent(this, ListeRendezVousActivity.class);
                startActivity(intent);
                finish(); // Optional: Finish the current activity if needed
            } else {
                Log.e("UpdateAppointment", "Failed to update appointment.");
            }
        } else {
            // Log an error if the appointmentId is not valid
            Log.e("UpdateAppointment", "Invalid appointmentId: " + appointmentId);
        }
    }
}
