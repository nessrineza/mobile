package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication1.models.Service;
import com.example.myapplication1.models.Offre;
import com.example.myapplication1.services.MyDatabaseOperations;

import java.util.ArrayList;
import java.util.List;

public class activity_creer_service extends AppCompatActivity {

    private EditText editTextNom;
    private OffreAdapter offreAdapter;
    private List<Offre> offreList;
    private MyDatabaseOperations databaseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_service);

        editTextNom = findViewById(R.id.editTextNom);
        offreList = new ArrayList<>();
        offreAdapter = new OffreAdapter(offreList);
        databaseOperations = new MyDatabaseOperations(this);

        // RecyclerView setup
        RecyclerView recyclerViewOffres = findViewById(R.id.recyclerViewOffres);
        recyclerViewOffres.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOffres.setAdapter(offreAdapter);
    }

    // Called when the "Ajouter Offre" button is clicked
    public void onAddOffreClick(View view) {
        // Retrieve Offre details from UI
        String offreNom = ""; // Replace with your logic
        String offreDescription = ""; // Replace with your logic

        // Create new Offre
        Offre newOffre = new Offre(0, offreNom, offreDescription);

        // Add Offre to the list
        offreAdapter.addOffre(newOffre);

        // Save the Offre to the database
        databaseOperations.open();
        long result = databaseOperations.insertOffre(newOffre);
        databaseOperations.close();

        if (result == -1) {
            // Handle error while saving Offre to the database
        }
    }

    // Called when the "Créer Service" button is clicked
    // Called when the "Créer Service" button is clicked
    // Called when the "Créer Service" button is clicked
    public void onCreateServiceClick(View view) {
        // Retrieve Service details from UI
        String serviceNom = editTextNom.getText().toString();

        // Create new Service
        Service newService = new Service(0, serviceNom, offreList);

        // Add Service to the database
        databaseOperations.open();


        // Add the current offreList from the adapter to the Service
        newService.setOffres(offreAdapter.getOffreList());

        long result = databaseOperations.insertService(newService);
        databaseOperations.close();

        if (result != -1) {
            // Service created successfully, you can navigate to another activity or show a success message
            finish(); // Close this activity
        } else {
            // Handle error, show a message to the user, etc.
        }
    }


}