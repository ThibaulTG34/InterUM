package com.example.interum;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Entreprise_CandidaturesNonTraitees extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidaturesnontraitees_entreprises);

        TextView nomCandidat0 = findViewById(R.id.NomCandidat0);
        TextView poste = findViewById(R.id.Poste0);


    }
}
