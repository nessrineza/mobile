package com.example.applicationnessrine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

    private int year, month, day, hour, minute;
    private DataSource dataSource;

    @Override
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
        String title = editTextTitle.getText().toString();
        String location = editTextLocation.getText().toString();

        // Format the date and time strings
        String date = String.format("%02d/%02d/%d", day, month + 1, year);
        String time = String.format("%02d:%02d", hour, minute);

        // Display a message
        String message = String.format(
                "Appointment saved:\nTitle: %s\nLocation: %s\nDate: %s\nTime: %s",
                title, location, date, time);
        showToast(message);

        // Save the appointment
        long appointmentId = dataSource.addAppointment(title, location, date, time);

        if (appointmentId != -1) {
            showToast("Appointment saved successfully!");
        } else {
            showToast("Failed to save appointment.");
        }
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
}