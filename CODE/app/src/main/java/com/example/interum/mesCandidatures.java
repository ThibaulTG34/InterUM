package com.example.interum;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class mesCandidatures extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mes_candidatures);

        TextView poste = findViewById(R.id.Poste1);
        TextView statut = findViewById(R.id.Statut);

        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        final String[] nomPoste = new String[1];
        final String[] etat = new String[1];

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("candidatures").whereEqualTo("userName", name);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String entrepriseId = document.getId();
                        Map<String, Object> candidatureData = document.getData();
                        nomPoste[0] = (String) candidatureData.get("nomOffre");
                        etat[0] = (String) candidatureData.get("etat");
                    }
                } else {
                    // Une erreur s'est produite lors de la récupération des offres
                    Toast.makeText(mesCandidatures.this, "Récupération des données de l'entreprise échouée.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        poste.setText(nomPoste[0]);
        statut.setText(etat[0]);

    }
}
