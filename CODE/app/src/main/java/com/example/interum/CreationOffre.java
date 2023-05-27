package com.example.interum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Obtenez une référence à la collection "offres"
        CollectionReference offresCollection = db.collection("offres");

        // Récupération des données de l'entreprise
        final String[] nomEntreprise = new String[1];
        Query query = db.collection("entreprise").whereEqualTo("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());
        // Exécutez la requête
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String entrepriseId = document.getId();
                        Map<String, Object> entrepriseData = document.getData();
                        nomEntreprise[0] = (String) entrepriseData.get("Nom");
                        // Utilisez les données de l'offre selon vos besoins

                        // Par exemple, vous pouvez afficher les détails de l'offre dans une liste
                    }
                } else {
                    // Une erreur s'est produite lors de la récupération des offres
                    Toast.makeText(CreationOffre.this, "Récupération des données de l'entreprise échouée.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Créez un objet Map pour stocker les données
        Map<String, Object> offreData = new HashMap<>();
        offreData.put("nom", name);
        offreData.put("description", description);
        offreData.put("periode", date);
        offreData.put("salaire", salary);
        offreData.put("Entreprise",nomEntreprise[0]);

// Ajoutez les données à la collection "offres"
        offresCollection.add(offreData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreationOffre.this, "Offre ajoutée avec succès.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreationOffre.this, "Échec de l'ajout de l'offre.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
