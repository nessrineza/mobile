package com.example.applicationnessrine;


// DataSource.java
import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;

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

    public long addAppointment(String title, String location, String date, String time) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TITLE, title);
        values.put(DBHelper.COLUMN_LOCATION, location);
        values.put(DBHelper.COLUMN_DATE, date);
        values.put(DBHelper.COLUMN_TIME, time);

        return database.insert(DBHelper.TABLE_APPOINTMENTS, null, values);
    }

    public Cursor getAllAppointments() {
        return database.query(DBHelper.TABLE_APPOINTMENTS, null, null, null, null, null, null);
    }
}
