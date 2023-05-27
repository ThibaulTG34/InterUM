package com.example.interum;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inscription extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private Boolean valide = false;
    private EditText telephone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription_candidat);

        Button valider = findViewById(R.id.valider);
        telephone = findViewById(R.id.editTextPhone);
        EditText nom = findViewById(R.id.editTextTextPersonName3);
        EditText prenom = findViewById(R.id.editTextTextPersonName4);
        EditText id = findViewById(R.id.editTextTextPersonName8);
        EditText password = findViewById(R.id.editTextTextPersonName9);

        //EditText birth = findViewById(R.id.editTextTextPersonName6);

        mAuth = FirebaseAuth.getInstance();

        valider.setOnClickListener(view -> {

            if(!nom.getText().toString().equals("") && !prenom.getText().toString().equals("") && !id.getText().toString().equals("") && !password.getText().toString().equals("") && !telephone.getText().toString().equals("") && password.getText().length() >= 8 && telephone.getText().length() == 10)
            {
                String identifiant = id.getText().toString();
                String passW = password.getText().toString();
                createAccount(identifiant, passW);


            }
            else
            {
                if(nom.getText().toString().equals(""))
                {
                    nom.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                if(prenom.getText().toString().equals(""))
                {
                    prenom.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                if(telephone.getText().toString().equals(""))
                {
                    telephone.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                if(id.getText().toString().equals(""))
                {
                    id.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                if(password.getText().toString().equals(""))
                {
                    password.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                if(password.getText().length() < 8)
                {
                    Toast.makeText(this, "8 caractères minimum", Toast.LENGTH_LONG).show();
                }
                if (telephone.getText().length() < 10)
                {
                    Toast.makeText(this, "Entrez un numéro de téléphone valide", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void onStart() {

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    private void reload() {
    }

    private void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intention = new Intent(Inscription.this, Validation_Inscription.class);
                        intention.putExtra("tel", telephone.getText().toString());
                        startActivity(intention);
                        valide = true;
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(Inscription.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        valide = false;
                    }
                });
    }
}
