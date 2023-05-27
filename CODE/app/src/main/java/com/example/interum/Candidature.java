package com.example.interum;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CCPCountry;
import com.hbb20.CountryCodePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Candidature extends AppCompatActivity {

    private static final int PICK_PDF_REQUEST = 1;

    Button btnImportPDF_CV;
    Button btnImportPDF_LM;

    private String selectedFileName_CV;
    private String selectedFileName_LM;

    boolean importedCV, importedLM;
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
        lieuoffre.setText(getIntent().getStringExtra("Entreprise"));

        Button validate = findViewById(R.id.validateCand);
        validate.setOnClickListener(view ->
        {
            EditText name = findViewById(R.id.editTextName);
            EditText firstName = findViewById(R.id.editTextFirstName);
            EditText date = findViewById(R.id.editTextDate);
            EditText city = findViewById(R.id.editTextCity);
            EditText tel = findViewById(R.id.editTextPhone2);
            CountryCodePicker country = findViewById(R.id.ccp);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> candidatureData = new HashMap<>();
            candidatureData.put("userName", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            candidatureData.put("nomOffre", nomOffre.getText().toString());
            candidatureData.put("entreprise", lieuoffre.getText().toString());
            candidatureData.put("nom", name.getText().toString());
            candidatureData.put("prenom", firstName.getText().toString());
            candidatureData.put("date", date.getText().toString());
            candidatureData.put("ville", city.getText().toString());
            candidatureData.put("tél", tel.getText().toString());
            candidatureData.put("pays", country.getSelectedCountryName().toString());
            candidatureData.put("etat", "En Attente");

            db.collection("candidature")
                    .add(candidatureData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(Candidature.this, "Candidature enregistrée avec succès.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Candidature.this, "Échec de l'enregistrement de la candidature.", Toast.LENGTH_SHORT).show();
                        }
                    });




        });

        btnImportPDF_CV = findViewById(R.id.upload);

        btnImportPDF_CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lancer l'intent pour sélectionner un fichier PDF
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                importedCV = true;
                startActivityForResult(Intent.createChooser(intent, "Sélectionner un fichier PDF"), PICK_PDF_REQUEST);
            }
        });

         btnImportPDF_LM= findViewById(R.id.Upload1);

        btnImportPDF_LM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lancer l'intent pour sélectionner un fichier PDF
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                importedLM = true;
                startActivityForResult(Intent.createChooser(intent, "Sélectionner un fichier PDF"), PICK_PDF_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedPDF = data.getData();

            // Récupérer le nom du fichier à partir de l'URI
            String[] filePathColumn = {MediaStore.Files.FileColumns.DISPLAY_NAME};
            Cursor cursor = getContentResolver().query(selectedPDF, filePathColumn, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                if(importedLM) selectedFileName_LM = cursor.getString(columnIndex);
                if(importedCV) selectedFileName_CV = cursor.getString(columnIndex);
                cursor.close();
            }

            // Mettre à jour le texte du bouton avec le nom du fichier
            if (selectedFileName_LM != null && importedLM) {
                    btnImportPDF_LM.setText(selectedFileName_LM);
                    importedLM = false;
            }
            if (selectedFileName_CV != null && importedCV) {
                btnImportPDF_CV.setText(selectedFileName_CV);
                importedCV = false;
            }

                Toast.makeText(this, "Fichier PDF sélectionné : " + selectedPDF.toString(), Toast.LENGTH_SHORT).show();
            // Upload du fichier PDF sur Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            String fileName = generateFileName(); // Génère un nom de fichier unique
            StorageReference pdfRef = storageRef.child("cv/" + fileName + ".pdf");

            pdfRef.putFile(selectedPDF)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Récupérer le lien de téléchargement du fichier PDF
                            pdfRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    // Enregistrer le lien de téléchargement dans Firebase Cloud Firestore
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Map<String, Object> cvData = new HashMap<>();
                                    cvData.put("downloadUrl", downloadUri.toString());

                                    db.collection("candidature")
                                            .add(cvData)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(Candidature.this, "Fichier PDF téléchargé avec succès.", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Candidature.this, "Échec du téléchargement du fichier PDF.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Candidature.this, "Échec du téléchargement du fichier PDF.", Toast.LENGTH_SHORT).show();
                        }
                    });



        }
    }

    private String generateFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String name = (FirebaseAuth.getInstance().getCurrentUser() != null) ? FirebaseAuth.getInstance().getCurrentUser().getDisplayName() : "anonymous" ;
        return "cv_" + name + "_" + timeStamp;
    }
}
