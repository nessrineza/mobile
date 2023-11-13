
package com.example.myapplication1.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication1.models.Offre;
import com.example.myapplication1.models.Service;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseOperations {

    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;

    public MyDatabaseOperations(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public int updateService(Service service) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_SERVICE_NAME, service.getNom());

        // Update the Service in the database
        return database.update(
                MyDatabaseHelper.TABLE_SERVICES,
                values,
                MyDatabaseHelper.COLUMN_SERVICE_ID + " = ?",
                new String[]{String.valueOf(service.getId())}
        );
    }
    public long insertService(Service service) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_SERVICE_NAME, service.getNom());

        long serviceId = database.insert(MyDatabaseHelper.TABLE_SERVICES, null, values);

        if (serviceId != -1) {
            // If the service insertion is successful, associate the Offres with the Service
            for (Offre offre : service.getOffres()) {
                ContentValues offreValues = new ContentValues();
                offreValues.put(MyDatabaseHelper.COLUMN_OFFRE_NAME, offre.getNom());
                offreValues.put(MyDatabaseHelper.COLUMN_OFFRE_DESCRIPTION, offre.getDescription());
                offreValues.put(MyDatabaseHelper.COLUMN_OFFRE_DESCRIPTION, serviceId);

                long offreId = database.insert(MyDatabaseHelper.TABLE_OFFRES, null, offreValues);

                if (offreId == -1) {
                    // Handle error while inserting Offre associated with the Service
                    return -1;
                }
            }
        }

        return serviceId;
    }

    public int deleteService(Service service) {
        long serviceId = service.getId();
        return database.delete(
                MyDatabaseHelper.TABLE_SERVICES,
                MyDatabaseHelper.COLUMN_SERVICE_ID + " = ?",
                new String[]{String.valueOf(serviceId)}
        );
    }



    public long insertOffre(Offre offre) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_OFFRE_NAME, offre.getNom());
        values.put(MyDatabaseHelper.COLUMN_OFFRE_DESCRIPTION, offre.getDescription());

        return database.insert(MyDatabaseHelper.TABLE_OFFRES, null, values);
    }

    public List<Service> getAllServices() {
        List<Service> serviceList = new ArrayList<>();
        Cursor cursor = database.query(
                MyDatabaseHelper.TABLE_SERVICES,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_SERVICE_ID));
                String name = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_SERVICE_NAME));

                Service service = new Service(id, name, null);
                serviceList.add(service);
            }
            cursor.close();
        }

        return serviceList;
    }

    public List<Offre> getAllOffres() {
        List<Offre> offreList = new ArrayList<>();
        Cursor cursor = database.query(
                MyDatabaseHelper.TABLE_OFFRES,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_OFFRE_ID));
                String name = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_OFFRE_NAME));
                String description = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_OFFRE_DESCRIPTION));

                Offre offre = new Offre(id, name, description);
                offreList.add(offre);
            }
            cursor.close();
        }

        return offreList;
    }
    // Delete a Service and its associated Offres


    // You can add other methods as needed (update, delete, getById, etc.)
}