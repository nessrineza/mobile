package com.example.applicationnessrine;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.applicationnessrine.model.Appointment;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Appointments.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_APPOINTMENTS = "appointments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_APPOINTMENTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TITLE
            + " text not null, " + COLUMN_LOCATION
            + " text not null, " + COLUMN_DATE
            + " text not null, " + COLUMN_TIME
            + " text not null);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Ajoutez cet appel pour initialiser la base de données
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        Log.d("DBHelper", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not needed for this example, but you might need it in a real app
    }
    public void updateAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", appointment.getTitle());
        values.put("location", appointment.getLocation());
        values.put("date", appointment.getDate());
        values.put("time", appointment.getTime());

        // Mettez à jour la ligne dans la table en fonction de l'ID
        db.update("appointment_table", values, "id = ?", new String[]{String.valueOf(appointment.getId())});

        // Fermez la connexion à la base de données
        db.close();
    }

}
