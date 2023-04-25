package com.example.interum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Inscription extends AppCompatActivity {
    private Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription_candidat);

        valider = findViewById(R.id.valider);

        valider.setOnClickListener(view -> {
            Intent intention = new Intent(Inscription.this, Validation_Inscription.class);
            startActivity(intention);
        });

    }
}
