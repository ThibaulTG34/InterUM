package com.example.interum;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConnexionEntreprise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterprise_connexion);

        Button valider = findViewById(R.id.valider);

        EditText siret = findViewById(R.id.editTextTextPersonName5);
        EditText nom = findViewById(R.id.editTextTextPersonName3);
        EditText id = findViewById(R.id.editTextTextPersonName8);
        EditText password = findViewById(R.id.editTextTextPersonName9);

        //EditText birth = findViewById(R.id.editTextTextPersonName6);


        valider.setOnClickListener(view -> {

            if (!nom.getText().toString().equals("") && !id.getText().toString().equals("") && !password.getText().toString().equals("") && !siret.getText().toString().equals("") && password.getText().length() >= 8) {
                Intent intention = new Intent(ConnexionEntreprise.this, accueil_entreprise.class);
                //nom.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));

                startActivity(intention);
            } else {
                if (nom.getText().toString().equals("")) {
                    nom.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                if (siret.getText().toString().equals("")) {
                    siret.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                if (id.getText().toString().equals("")) {
                    id.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                if (password.getText().toString().equals("")) {
                    password.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                if (password.getText().length() < 8) {
                    Toast.makeText(this, "8 caractères minimum", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
