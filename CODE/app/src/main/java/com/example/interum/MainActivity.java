package com.example.interum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button candidate = findViewById(R.id.button2);
        Button enterprise = findViewById(R.id.button3);
        Button agence = findViewById(R.id.button4);



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

    }
}