package com.example.interum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class accueil_entreprise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        switch(MainActivity.userType)
        {
            case COMPANY:
                setContentView(R.layout.accueil_entreprise);
                break;
            case AGENCY:
                setContentView(R.layout.accueil_agence);
                break;
        }

        Button addOffer = findViewById(R.id.button16);
        addOffer.setOnClickListener(view -> {
            Intent intent = new Intent(accueil_entreprise.this, CreationOffre.class);
            startActivity(intent);
        });

    }
}
