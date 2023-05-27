package com.example.interum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentification extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_candidate);

        Button inscription = findViewById(R.id.button7);
        Button connexion = findViewById(R.id.button9);

        mAuth = FirebaseAuth.getInstance();

        EditText id = findViewById(R.id.editTextTextPersonName);
        EditText password = findViewById(R.id.editTextTextPersonName2);

        connexion.setOnClickListener(view -> {
            if(!id.getText().toString().equals("") && !password.getText().toString().equals(""))
            {
                String identifiant = id.getText().toString();
                String passW = password.getText().toString();
                signIn(identifiant, passW);
            }
        });

        inscription.setOnClickListener(view -> {

            Intent intention;
            switch(MainActivity.userType)
            {
                case COMPANY:
                    intention = new Intent(Authentification.this, ConnexionEntreprise.class);
                    break;
                case AGENCY:
                    intention = new Intent(Authentification.this, ConnexionAgence.class);
                    break;
                default: //Candidate
                    intention = new Intent(Authentification.this, Inscription.class);
                    break;
            }
            startActivity(intention);
        });
    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intention;
                        switch(MainActivity.userType)
                        {
                            case COMPANY:
                            case AGENCY:
                                intention = new Intent(Authentification.this, accueil_entreprise.class);
                                break;
                            default: //Candidate
                                intention = new Intent(Authentification.this, geolocalisation.class);
                                break;
                        }
                        startActivity(intention);
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(Authentification.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

    }
}
