package com.example.interum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.rpc.context.AttributeContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ListOffre extends AppCompatActivity {

    private static final String TAG = "DATABASE : ";
    private static final int NB_OFFRE = 3;
    private final Boolean _switchIMAGE = false;
    private final Boolean _switchDESCRIPTION = false;
    private int visibily;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_offre);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextView titre = findViewById(R.id.textView22);
        TextView entreprise = findViewById(R.id.textView23);
        TextView ville = findViewById(R.id.textView24);

        TextView titre2 = findViewById(R.id.textView25);
        TextView entreprise2 = findViewById(R.id.textView26);
        TextView ville2 = findViewById(R.id.textView27);

        TextView titre3 = findViewById(R.id.textView30);
        TextView entreprise3 = findViewById(R.id.textView31);
        TextView ville3 = findViewById(R.id.textView40);


        ImageButton hideButton1 = findViewById(R.id.imageButton6);
        ImageButton hideButton2 = findViewById(R.id.imageButton8);
        ImageButton hideButton3 = findViewById(R.id.imageButton9);

        Button seeMoreButton1 = findViewById(R.id.button16);
        Button seeMoreButton2 = findViewById(R.id.button17);
        Button seeMoreButton3 = findViewById(R.id.button19);

        TextView[] titres = {titre, titre2, titre3};
        TextView[] entreprises = {entreprise, entreprise2, entreprise3};
        TextView[] villes_tv = {ville, ville2, ville3};

        ImageButton [] imageButtons = {hideButton1, hideButton2, hideButton3};
        Button [] buttons = {seeMoreButton1, seeMoreButton2, seeMoreButton3};
        Button [] candi = {findViewById(R.id.candidater0), findViewById(R.id.candidater1), findViewById(R.id.candidater2)};

        for (int i=0; i<titres.length; i++) {
            if(titres[i].getText().toString().equals(""))
            {
                imageButtons[i].setVisibility(View.INVISIBLE);
                buttons[i].setVisibility(View.INVISIBLE);
                candi[i].setVisibility(View.INVISIBLE);
            }
            else
            {
                imageButtons[i].setVisibility(View.VISIBLE);
                buttons[i].setVisibility(View.VISIBLE);
                candi[i].setVisibility(View.VISIBLE);

            }
        }

        String city = getIntent().getStringExtra("location");

        if(city!= null && !city.equals(""))
        {
            db.collection("offres")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (int i=0; i<task.getResult().size(); i++) {
                                titres[i].setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(i).getData()).get("nom"));
                                entreprises[i].setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(i).getData()).get("Entreprise"));
                                villes_tv[i].setText(city);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    });
        }
        else
        {
            db.collection("offres")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for(int i=0; i<task.getResult().size(); i++) {
                                titres[i].setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(i).getData()).get("nom"));
                                entreprises[i].setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(i).getData()).get("Entreprise"));
                                villes_tv[i].setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(i).getData()).get("ville"));
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    });
        }
        boolean isConnnected = FirebaseAuth.getInstance().getCurrentUser() != null;

        Intent candidating = new Intent(ListOffre.this, Candidature.class);
        findViewById(R.id.candidater0).setOnClickListener(view->{
            if(isConnnected) {
                candidating.putExtra("NomOffre", titres[0].getText().toString());
                candidating.putExtra("Entreprise", entreprises[0].getText().toString());
                startActivity(candidating);

            }
            else
                startActivity(new Intent(ListOffre.this, Authentification.class));

        });

        findViewById(R.id.candidater1).setOnClickListener(view->{
            if(isConnnected) {
                candidating.putExtra("NomOffre", titres[1].getText().toString());
                candidating.putExtra("Entreprise", entreprises[1].getText().toString());
                startActivity(candidating);
            }
            else
                startActivity(new Intent(ListOffre.this, Authentification.class));
        });

        findViewById(R.id.candidater2).setOnClickListener(view->{
            if(isConnnected){
                candidating.putExtra("NomOffre", titres[2].getText().toString());
                candidating.putExtra("Entreprise", entreprises[1].getText().toString());
                startActivity(candidating);
            }
            else
                startActivity(new Intent(ListOffre.this, Authentification.class));
        });
    }
}
