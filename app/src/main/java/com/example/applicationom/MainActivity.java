package com.example.applicationom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    Button visage;
    Button yeux;
    Button levres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        visage = findViewById(R.id.button_visage);
        visage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Interface22.class);
                startActivity(intent);
            }
        });
        yeux = findViewById(R.id.button_yeux);
        yeux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InterfaceYeux.class);
                startActivity(intent);
            }
        });

        levres = findViewById(R.id.button_levres);
        levres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Interfacecheveux.class);
                startActivity(intent);
            }
        });

    }


        }


