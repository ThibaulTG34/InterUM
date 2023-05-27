package com.example.interum;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ConnexionEntreprise extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private boolean valide;

    String nom, siret, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterprise_connexion);

        Button valider = findViewById(R.id.valider);

        EditText siret = findViewById(R.id.editTextTextPersonName5);
        EditText nom = findViewById(R.id.editTextTextPersonName3);
        EditText id = findViewById(R.id.editTextTextPersonName8);
        EditText password = findViewById(R.id.editTextTextPersonName9);

        this.nom = nom.getText().toString();
        this.siret = siret.getText().toString();
        this.id = id.getText().toString();


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

    private void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intention = new Intent(ConnexionEntreprise.this, Validation_Inscription.class);

                        // TODO : déplacer dans validation inscription en envoyant en extra dans l'intent pour avoir le currentUser id.
                        intention.putExtra("NomEntreprise", this.nom);
                        intention.putExtra("Siret", this.siret);
                        intention.putExtra("IdEntreprise", this.id);
                        //intention.putExtra("tel", telephone.getText().toString());
                        startActivity(intention);
                        valide = true;
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(ConnexionEntreprise.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        valide = false;
                    }
                });
    }
}
