package com.example.interum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button candidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        candidate = findViewById(R.id.button2);


        candidate.setOnClickListener(view -> {
            Intent intention = new Intent(MainActivity.this, Authentification.class);
            startActivity(intention);
        });
    }
}