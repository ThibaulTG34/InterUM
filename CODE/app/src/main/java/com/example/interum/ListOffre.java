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
    private static final int NB_OFFRE = 5;
    private Boolean _switchIMAGE = false;
    private Boolean _switchDESCRIPTION = false;
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
        TextView ville3 = findViewById(R.id.textView32);


        String city = getIntent().getStringExtra("location");
        //TextView titre4 = findViewById(R.id.textView34);
        //TextView entreprise4 = findViewById(R.id.textView35);
        //TextView ville4 = findViewById(R.id.textView36);

        //TextView titre5 = findViewById(R.id.textView38);
        //TextView entreprise5 = findViewById(R.id.textView39);
        //TextView ville5 = findViewById(R.id.textView40);

        ImageButton hideButton1 = findViewById(R.id.imageButton6);
        ImageButton hideButton2 = findViewById(R.id.imageButton7);
        ImageButton hideButton3 = findViewById(R.id.imageButton8);
        ImageButton hideButton4 = findViewById(R.id.imageButton9);
        ImageButton hideButton5 = findViewById(R.id.imageButton10);

        Button seeMoreButton1 = findViewById(R.id.button16);
        Button seeMoreButton2 = findViewById(R.id.button17);
        Button seeMoreButton3 = findViewById(R.id.button18);
        Button seeMoreButton4 = findViewById(R.id.button19);
        Button seeMoreButton5 = findViewById(R.id.button20);

        TextView Description1 = findViewById(R.id.textView41);
        Button seeLess = findViewById(R.id.button21);

        //TextView[] titres = {titre, titre2, titre3, titre4, titre5};
        //TextView[] entreprises = {entreprise, entreprise2, entreprise3, entreprise4, entreprise5};
        //TextView[] villes_tv = {ville, ville2, ville3, ville4, ville5};

        ImageButton [] imageButtons = {hideButton1, hideButton2, hideButton3, hideButton4, hideButton5};
        Button [] buttons = {seeMoreButton1, seeMoreButton2, seeMoreButton3, seeMoreButton4, seeMoreButton5};

        Bundle extras = getIntent().getExtras();
        //String city = extras.getString("location");

    }
}
