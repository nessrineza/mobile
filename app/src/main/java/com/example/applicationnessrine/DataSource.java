package com.example.applicationnessrine;


// DataSource.java
import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.applicationnessrine.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public DataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Dans la classe DataSource

    public long insertAppointment(String title, String location, String date, String time) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TITLE, title);
        values.put(DBHelper.COLUMN_LOCATION, location);
        values.put(DBHelper.COLUMN_DATE, date);
        values.put(DBHelper.COLUMN_TIME, time);

        long result = database.insert(DBHelper.TABLE_APPOINTMENTS, null, values);

        if (result != -1) {
            Log.d("DataSource", "Appointment inserted successfully: " + title);
        } else {
            Log.e("DataSource", "Failed to insert appointment: " + title);
        }

        return result;
    }




    public Cursor getAllAppointments() {
        return database.query(DBHelper.TABLE_APPOINTMENTS, null, null, null, null, null, null);
    }
    public int updateAppointmentById(long appointmentId, String title, String location, String date, String time) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TITLE, title);
        values.put(DBHelper.COLUMN_LOCATION, location);
        values.put(DBHelper.COLUMN_DATE, date);
        values.put(DBHelper.COLUMN_TIME, time);

        String whereClause = DBHelper.COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(appointmentId)};

        int rowsUpdated = database.update(DBHelper.TABLE_APPOINTMENTS, values, whereClause, whereArgs);

        if (rowsUpdated > 0) {
            Log.d("DataSource", "Appointment updated successfully. Rows affected: " + rowsUpdated);
        } else {
            Log.e("DataSource", "Failed to update appointment.");
        }

        return rowsUpdated;
    }


    public List<Appointment> getAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {DBHelper.COLUMN_ID, DBHelper.COLUMN_TITLE, DBHelper.COLUMN_LOCATION, DBHelper.COLUMN_DATE, DBHelper.COLUMN_TIME};

        Cursor cursor = database.query(
                DBHelper.TABLE_APPOINTMENTS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TITLE));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOCATION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DATE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TIME));

            Appointment appointment = new Appointment();
            appointment.setId(id);
            appointment.setTitle(title);
            appointment.setLocation(location);
            appointment.setDate(date);
            appointment.setTime(time);

            appointments.add(appointment);
        }

        cursor.close();

        return appointments;
    }
    public Appointment getAppointmentById(long appointmentId) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.COLUMN_ID,
                DBHelper.COLUMN_TITLE,
                DBHelper.COLUMN_LOCATION,
                DBHelper.COLUMN_DATE,
                DBHelper.COLUMN_TIME
        };

        String selection = DBHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(appointmentId)};

        Cursor cursor = database.query(
                DBHelper.TABLE_APPOINTMENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Appointment appointment = null;
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TITLE));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOCATION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DATE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TIME));

            appointment = new Appointment();
            appointment.setId(id);
            appointment.setTitle(title);
            appointment.setLocation(location);
            appointment.setDate(date);
            appointment.setTime(time);
        }

        cursor.close();

        return appointment;
    }
    public int deleteAppointment(long appointmentId) {
        String whereClause = DBHelper.COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(appointmentId)};
        return database.delete(DBHelper.TABLE_APPOINTMENTS, whereClause, whereArgs);
    }


}
