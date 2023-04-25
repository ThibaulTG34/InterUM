package com.example.interum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Authentification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_candidate);

        Button inscription = findViewById(R.id.button7);

        inscription.setOnClickListener(view -> {
            Intent intention = new Intent(Authentification.this, Inscription.class);
            startActivity(intention);
        });
    }
}
