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

    public AppointmentAdapter(Context context, List<Appointment> appointments) {
        super(context, 0, appointments);
    }

    public interface UpdateClickListener {
        void onUpdateClick(long appointmentId);
    }

    private UpdateClickListener updateClickListener;

    public void setUpdateClickListener(UpdateClickListener listener) {
        this.updateClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_appointment, parent, false);
        }

        // Obtain the Appointment object at this position
        Appointment appointment = getItem(position);


        // Fill the views with appointment data
        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewDate = convertView.findViewById(R.id.textViewDate);
        Button updateButton = convertView.findViewById(R.id.updateButton);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        // Setting text for name and date

            textViewName.setText(appointment.getTitle());
            textViewDate.setText(appointment.getDate());

            // Adding a click listener for the update button
            updateButton.setOnClickListener(v -> {
                if (updateClickListener != null) {
                    Log.d("AppointmentAdapter", "Update button clicked for appointment ID: " + appointment.getId());
                    updateClickListener.onUpdateClick(appointment.getId());
                }
            });
            deleteButton.setOnClickListener(v -> {
                // Show the delete confirmation dialog
                showDeleteConfirmationDialog(getContext(), appointment.getId());
            });

        return convertView;
    }

    private void showDeleteConfirmationDialog(Context context, long appointmentId) {
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

    private static void deleteAppointment(Context context, long appointmentId) {
        DataSource dataSource = new DataSource(context);
        dataSource.open();
        int deletedRows = dataSource.deleteAppointment(appointmentId);
        dataSource.close();

        if (deletedRows > 0) {
            Log.d("AppointmentAdapter", "Appointment deleted successfully. Rows affected: " + deletedRows);
        } else {
            Log.e("AppointmentAdapter", "Failed to delete appointment.");
        }
    }
}
