package com.example.interum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button candidate = findViewById(R.id.button2);
        Button enterprise = findViewById(R.id.button3);
        Button agence = findViewById(R.id.button4);



        candidate.setOnClickListener(view -> {
            Intent intention = new Intent(MainActivity.this, Authentification.class);
            startActivity(intention);
        });
        enterprise.setOnClickListener(view -> {
            Intent intention = new Intent(MainActivity.this, ConnexionEntreprise.class);
            startActivity(intention);
        });
        agence.setOnClickListener(view -> {
            Intent intention = new Intent(MainActivity.this, ConnexionAgence.class);
            startActivity(intention);
        });

    }
}