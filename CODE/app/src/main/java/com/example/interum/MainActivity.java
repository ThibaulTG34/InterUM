package com.example.interum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public enum UserType
    {
        CANDIDATE,
        COMPANY,
        AGENCY,
        MANAGER,
        SUPER_MANAGER;
    };

    public static UserType userType;

    private static final String TAG = "FIRESTORE : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button candidate = findViewById(R.id.button2);
        Button enterprise = findViewById(R.id.button3);
        Button agence = findViewById(R.id.button4);
        Button voirOffres = findViewById(R.id.button);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String collectionName = "refuser";

        String[] offres = {"offre1", "offre2", "offre3", "offre4", "offre5", "offre6", "offre7", "offre8", "offre9", "offre10"};
        String[] villes = {"Montpellier", "Paris", "Saint-Aunes", "Bordeaux", "Montpellier", "Lyon", "Marseille", "Grenoble", "Perpignan", "Lilles"};
        String[] titres = {"Ingenieur Web H/F", "Stage Level Designer", "Agent de réception", "Conseiller en vente", "Conseiller en vente", "Carriste", "Developpeur Java H/F", "Ingénieur développement Python", "Developpeur Web H/F", "Manutentionnaire"};

        String[] entreprises = {
                "CapGemini",
                "Ubisoft",
                "Carrefour",
                "Celio",
                "Castorama",
                "LeClerc Drive",
                "CapGemini",
                "Free-work",
                "Credit agricole",
                "Loxam"
        };

        for (int i = 0; i < offres.length; i++) {
            String offre = offres[i];
            String entreprise = entreprises[i];
            String ville = villes[i];
            String titre = titres[i];

            Map<String, Object> documentData = new HashMap<>();
            documentData.put("entreprise", entreprise);
            documentData.put("titre", titre);
            documentData.put("ville", ville);

            db.collection(collectionName)
                    .document(offre)
                    .set(documentData)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }


        candidate.setOnClickListener(view -> {
            Intent intention = new Intent(MainActivity.this, Authentification.class);
            startActivity(intention);
        });
        enterprise.setOnClickListener(view -> {
            Intent intention = new Intent(MainActivity.this, Authentification.class);
            userType = UserType.COMPANY;
            startActivity(intention);
        });
        agence.setOnClickListener(view -> {
            Intent intention = new Intent(MainActivity.this, Authentification.class);
            userType = UserType.AGENCY;
            startActivity(intention);
        });
        voirOffres.setOnClickListener(view -> {
            Intent intention = new Intent(MainActivity.this, ListOffre.class);
            intention.putExtra("location", "");
            startActivity(intention);
        });

    }
}