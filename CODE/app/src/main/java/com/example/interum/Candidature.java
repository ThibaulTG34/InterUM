package com.example.interum;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Candidature extends AppCompatActivity {

    private static final int PICK_PDF_REQUEST = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidature);
        TextView nomOffre = findViewById(R.id.NomOffre);
        nomOffre.setText(getIntent().getStringExtra("NomOffre"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nomOffre.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }

        TextView lieuoffre = findViewById(R.id.lieuoffre);
        lieuoffre.setText(getIntent().getStringExtra("Ville"));

        Button validate = findViewById(R.id.validateCand);
        validate.setOnClickListener(view ->
        {

        });

        Button btnImportPDF_CV = findViewById(R.id.upload);

        btnImportPDF_CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lancer l'intent pour sélectionner un fichier PDF
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Sélectionner un fichier PDF"), PICK_PDF_REQUEST);
            }
        });

        Button btnImportPDF_LM = findViewById(R.id.Upload1);

        btnImportPDF_LM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lancer l'intent pour sélectionner un fichier PDF
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Sélectionner un fichier PDF"), PICK_PDF_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedPDF = data.getData();

            // Vous pouvez utiliser l'URI du fichier PDF sélectionné ici, par exemple pour l'afficher ou le traiter.

            Toast.makeText(this, "Fichier PDF sélectionné : " + selectedPDF.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
