package com.example.interum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuCandidat extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_candidat);
        Button seeoffer = findViewById(R.id.SeeOffer);
        seeoffer.setOnClickListener(view ->{
            startActivity(new Intent(MenuCandidat.this, ListOffre.class));
        });

        findViewById(R.id.SeeCandidatures).setOnClickListener(view ->{
            startActivity(new Intent(MenuCandidat.this, ListOffre.class));
        });

        findViewById(R.id.SeeJobs).setOnClickListener(view ->{
            startActivity(new Intent(MenuCandidat.this, ListOffre.class));
        });

        findViewById(R.id.SeeAlerts).setOnClickListener(view ->{
            startActivity(new Intent(MenuCandidat.this, ListOffre.class));
        });
    }
}
