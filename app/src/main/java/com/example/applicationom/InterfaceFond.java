package com.example.applicationom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class InterfaceFond extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_fond);
        Button buttonMinusFT1 = findViewById(R.id.button_diminuer_ft1);
        Button buttonPlusFT1 = findViewById(R.id.button_augmenter_ft1);
        TextView quantityTextFT1 = findViewById(R.id.quantite_ft1);


    }
}