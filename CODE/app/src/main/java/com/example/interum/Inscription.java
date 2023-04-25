package com.example.interum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Inscription extends AppCompatActivity {
    private Button valider;
    private EditText telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription_candidat);

        valider = findViewById(R.id.valider);
        telephone = findViewById(R.id.editTextPhone);

        valider.setOnClickListener(view -> {
            Intent intention = new Intent(Inscription.this, Validation_Inscription.class);

            //intention.putExtra("tel", telephone.getText().toString());
            startActivity(intention);
        });

    }
}
