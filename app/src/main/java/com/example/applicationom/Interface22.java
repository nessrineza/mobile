package com.example.applicationom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Interface22 extends AppCompatActivity {
Button fond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface22);
        fond = findViewById(R.id.button_fond);
        fond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Interface22.this, InterfaceFond.class);
                startActivity(intent);
            }


        });

    }
}
