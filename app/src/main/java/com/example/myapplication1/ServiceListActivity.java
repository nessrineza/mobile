package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.models.Service;
import com.example.myapplication1.services.MyDatabaseOperations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ServiceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyDatabaseOperations databaseOperations;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        databaseOperations = new MyDatabaseOperations(this);

        // Set up the RecyclerView
        displayServiceList();

        // Set click listener for FAB
        fab.setOnClickListener(view -> {
            // Navigate to create service activity
            Intent intent = new Intent(ServiceListActivity.this, activity_creer_service.class);
            startActivity(intent);
        });
    }

    private void displayServiceList() {
        databaseOperations.open();
        List<Service> serviceList = databaseOperations.getAllServices();
        databaseOperations.close();

        // Set up the RecyclerView
        ServiceAdapter adapter = new ServiceAdapter(serviceList,databaseOperations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}