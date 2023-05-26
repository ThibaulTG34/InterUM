package com.example.interum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreationOffre extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_offre);

        Button valider = findViewById(R.id.valider2);

        EditText name = findViewById(R.id.editTextOffreNom);
        EditText description = findViewById(R.id.editTextDescription);
        EditText periode = findViewById(R.id.editTextPeriode);
        EditText salary = findViewById(R.id.editTextSalary);
        valider.setOnClickListener(view ->{
            createOffer(
                    name.getText().toString(),
                    description.getText().toString(),
                    periode.getText().toString(),
                    Integer.parseInt(salary.getText().toString())
            );
        });
    }

    void createOffer(String name, String description, String date, int salary)
    {
        Toast.makeText(CreationOffre.this, "Registered offer.",
                Toast.LENGTH_SHORT).show();
    }
}
