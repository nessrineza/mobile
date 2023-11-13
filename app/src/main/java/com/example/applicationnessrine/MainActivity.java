package com.example.applicationnessrine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private TextView textViewDate;
    private TextView textViewTime;
    private EditText editTextTitle;
    private EditText editTextLocation;
    private EditText editTextAppointmentId; // Ajoutez cette ligne
    private long appointmentId;


    private int year, month, day, hour, minute;
    private DataSource dataSource;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextLocation = findViewById(R.id.editTextLocation);

        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        dataSource = new DataSource(this);
        dataSource.open();

        // Initialisez editTextAppointmentId après avoir ouvert la source de données
        editTextAppointmentId = findViewById(R.id.editTextAppointmentId);
    }

    public void pickDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) -> {
                    year = selectedYear;
                    month = selectedMonth;
                    day = selectedDay;
                    updateDateTextView();
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    public void pickTime(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (TimePicker view12, int selectedHour, int selectedMinute) -> {
                    hour = selectedHour;
                    minute = selectedMinute;
                    updateTimeTextView();
                },
                hour, minute, true
        );
        timePickerDialog.show();
    }

    private void updateDateTextView() {
        textViewDate.setText(String.format("Selected Date: %02d/%02d/%d", day, month + 1, year));
    }

    private void updateTimeTextView() {
        textViewTime.setText(String.format("Selected Time: %02d:%02d", hour, minute));
    }

    public void saveAppointment(View view) {
        // Récupérer les informations du rendez-vous depuis les champs de texte
        String title = editTextTitle.getText().toString();
        String location = editTextLocation.getText().toString();
        String date = String.format("%02d/%02d/%d", day, month + 1, year);
        String time = String.format("%02d:%02d", hour, minute);

        // Ajouter le rendez-vous à la base de données
        long appointmentId = dataSource.insertAppointment(title, location, date, time);

        if (appointmentId != -1) {
            showToast("Appointment saved successfully!");

            // Rediriger vers l'activité qui affiche la liste des rendez-vous
            Intent intent = new Intent(this, ListeRendezVousActivity.class);
            startActivity(intent);
            finish(); // Facultatif : fermer l'activité actuelle pour éviter le retour
        } else {
            showToast("Failed to save appointment.");
        }
        Intent intent = new Intent(this, ListeRendezVousActivity.class);
        startActivity(intent);
    }



    private void showToast(String message) {
        // Display a toast message (you might want to replace this with a more appropriate UI)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
    public void updateAppointment(View view) {
        // Récupérez l'ID de l'élément à mettre à jour
        String appointmentIdText = editTextAppointmentId.getText().toString();
        if (!appointmentIdText.isEmpty()) {
            long appointmentId = Long.parseLong(appointmentIdText);

            // Récupérez les informations mises à jour
            String title = editTextTitle.getText().toString();
            String location = editTextLocation.getText().toString();
            String date = String.format("%02d/%02d/%d", day, month + 1, year);
            String time = String.format("%02d:%02d", hour, minute);

            // Vérifiez que les champs requis ne sont pas vides
            if (title.isEmpty() || location.isEmpty()) {
                showToast("Title and location are required fields");
                return;
            }

            // Mettez à jour l'élément dans la base de données
            int rowsAffected = dataSource.updateAppointmentById(appointmentId, title, location, date, time);

            // Affichez un message en fonction du succès de la mise à jour
            if (rowsAffected > 0) {
                showToast("Appointment updated successfully!");
            } else {
                showToast("Failed to update appointment.");
            }
        } else {
            showToast("Appointment ID is required");
        }
    }


}