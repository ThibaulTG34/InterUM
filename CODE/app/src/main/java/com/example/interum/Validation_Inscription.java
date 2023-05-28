package com.example.interum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Validation_Inscription extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseUser user;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuthSettings firebaseAuthSettings;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validation_inscription);

        Button valider = findViewById(R.id.button8);
        Button ok = findViewById(R.id.button15);
        TextView enterCode = findViewById(R.id.textView17);
        EditText number = findViewById(R.id.editTextNumber);

        CheckBox email = findViewById(R.id.checkBox);
        CheckBox telephone = findViewById(R.id.checkBox2);

        user = auth.getCurrentUser();
        firebaseAuthSettings = auth.getFirebaseAuthSettings();

        ok.setOnClickListener(view -> {
            String verificationCode = number.getText().toString().trim();
            if(!verificationCode.equals(""))
            {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                signInWithPhoneAuthCredential(credential);
            }
        });

        valider.setOnClickListener(view -> {

            if(email.isChecked()) {
                assert user != null;
                user.sendEmailVerification()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                            }
                        });
            }

            if(telephone.isChecked())
            {
                String phoneNumber;
                StringBuilder tel = new StringBuilder();
                Bundle extras = getIntent().getExtras();
                assert extras != null;
                phoneNumber = extras.getString("tel");
                tel.append("+33 ");
                String[] list = phoneNumber.split("");
                for (int i = 0; i < phoneNumber.length()-1; i+=2)
                {
                    tel.append((String) (list[i] + list[i + 1]));
                    tel.append(" ");
                }
                Log.d(TAG, tel.toString());
                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(Validation_Inscription.this, "ici", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        Log.d(TAG, "onCodeSent:" + verificationId);
                        mVerificationId = verificationId;
                        mResendToken = token;
                    }
                };
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(auth)
                                .setPhoneNumber(String.valueOf(tel))
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(this)
                                .setCallbacks(mCallbacks)
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);

                enterCode.setVisibility(View.VISIBLE);
                number.setVisibility(View.VISIBLE);
                ok.setVisibility(View.VISIBLE);
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        Intent intention;
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(MainActivity.userType == MainActivity.UserType.COMPANY)
                        {
                            intention = new Intent(Validation_Inscription.this, accueil_entreprise.class);
                            String userID = user.getUid();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Map<String, Object> entrepriseData = new HashMap<>();
                            entrepriseData.put("Nom", getIntent().getStringExtra("NomEntreprise"));
                            entrepriseData.put("Siret", getIntent().getStringExtra("Siret"));
                            entrepriseData.put("Id", getIntent().getStringExtra("IdEntreprise"));

                            db.collection("entreprises").document(userID)
                                    .set(entrepriseData)
                                    .addOnSuccessListener(aVoid -> Toast.makeText(Validation_Inscription.this, "Entreprise enregistrée avec succès.", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(Validation_Inscription.this, "Échec de l'enregistrement de l'entreprise.", Toast.LENGTH_SHORT).show());
                            intention.putExtra("nom", getIntent().getStringExtra("NomEntreprise"));
                            intention = new Intent(Validation_Inscription.this, accueil_entreprise.class);
                        }
                        else
                        {
                            intention = new Intent(Validation_Inscription.this, MenuCandidat.class);
                        }
                        startActivity(intention);
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.reload()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if(user.isEmailVerified()) {
                                Intent intention;
                                if (MainActivity.userType == MainActivity.UserType.COMPANY) {
                                    intention = new Intent(Validation_Inscription.this, accueil_entreprise.class);
                                    String userID = user.getUid();
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Map<String, Object> entrepriseData = new HashMap<>();
                                    entrepriseData.put("Nom", getIntent().getStringExtra("NomEntreprise"));
                                    entrepriseData.put("Siret", getIntent().getStringExtra("Siret"));
                                    entrepriseData.put("Id", getIntent().getStringExtra("IdEntreprise"));

                                    db.collection("entreprises").document(userID)
                                            .set(entrepriseData)
                                            .addOnSuccessListener(aVoid -> Toast.makeText(Validation_Inscription.this, "Entreprise enregistrée avec succès.", Toast.LENGTH_SHORT).show())
                                            .addOnFailureListener(e -> Toast.makeText(Validation_Inscription.this, "Échec de l'enregistrement de l'entreprise.", Toast.LENGTH_SHORT).show());
                                    intention.putExtra("nom", getIntent().getStringExtra("NomEntreprise"));

                                } else {
                                    intention = new Intent(Validation_Inscription.this, MenuCandidat.class);
                                }
                                startActivity(intention);
                            }
                        } else {
                            Toast.makeText(Validation_Inscription.this, "L'envoi de mail de confirmation a échoué.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }//fin de onResume

}
