package com.example.interum;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ListOffre extends AppCompatActivity {

    private static final String TAG = "DATABASE : ";

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
        TextView ville3 = findViewById(R.id.textView32);

        Bundle extras = getIntent().getExtras();
        String city = extras.getString("location");

        db.collection("accepter")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if(!Objects.equals(city, "")) {

                            titre.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(0).getData()).get("titre"));
                            entreprise.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(0).getData()).get("entreprise"));
                            ville.setText(city);

                            titre2.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(1).getData()).get("titre"));
                            entreprise2.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(1).getData()).get("entreprise"));
                            ville2.setText(city);

                            titre3.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(2).getData()).get("titre"));
                            entreprise3.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(2).getData()).get("entreprise"));
                            ville3.setText(city);
                        }
                        else {

                            titre.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(0).getData()).get("titre"));
                            entreprise.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(0).getData()).get("entreprise"));
                            ville.setText("Toulouse");

                            titre2.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(1).getData()).get("titre"));
                            entreprise2.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(1).getData()).get("entreprise"));
                            ville2.setText("Montpellier");

                            titre3.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(2).getData()).get("titre"));
                            entreprise3.setText((String) Objects.requireNonNull(task.getResult().getDocuments().get(2).getData()).get("entreprise"));
                            ville3.setText("Nîmes");
                        }

                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }
}
