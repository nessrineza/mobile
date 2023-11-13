// MyDatabaseHelper.java
package com.example.myapplication1.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    // Define your database name and version
    private static final String DATABASE_NAME = "my_services.db";
    private static final int DATABASE_VERSION = 1;

    // Define your table and column names for Service
    public static final String TABLE_SERVICES = "services";
    public static final String COLUMN_SERVICE_ID = "_id";
    public static final String COLUMN_SERVICE_NAME = "name";

    // Define your table and column names for Offre
    public static final String TABLE_OFFRES = "offres";
    public static final String COLUMN_OFFRE_ID = "_id";
    public static final String COLUMN_OFFRE_NAME = "name";
    public static final String COLUMN_OFFRE_DESCRIPTION = "description";

    // Define your create table queries
    private static final String CREATE_SERVICES_TABLE =
            "CREATE TABLE " + TABLE_SERVICES + " (" +
                    COLUMN_SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_SERVICE_NAME + " TEXT NOT NULL);";

    private static final String CREATE_OFFRES_TABLE =
            "CREATE TABLE " + TABLE_OFFRES + " (" +
                    COLUMN_OFFRE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_OFFRE_NAME + " TEXT NOT NULL, " +
                    COLUMN_OFFRE_DESCRIPTION + " TEXT);";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your tables
        db.execSQL(CREATE_SERVICES_TABLE);
        db.execSQL(CREATE_OFFRES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        // This method is called when the database version changes
        // For simplicity, you can drop existing tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFRES);
        onCreate(db);
    }
}
