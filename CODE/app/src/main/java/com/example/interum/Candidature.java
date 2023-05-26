package com.example.interum;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Candidature extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidature);
        TextView nomOffre = findViewById(R.id.NomOffre);
        nomOffre.setText(getIntent().getStringExtra("NomOffre"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nomOffre.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }

        TextView lieuoffre = findViewById(R.id.lieuoffre);
        lieuoffre.setText(getIntent().getStringExtra("Ville"));

        Button validate = findViewById(R.id.validateCand);
        validate.setOnClickListener(view ->
        {

        });
    }
}
