package com.example.interum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Validation_Inscription extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseUser user;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuthSettings firebaseAuthSettings;

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

        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseAuthSettings = auth.getFirebaseAuthSettings();



        valider.setOnClickListener(view -> {

            if(email.isChecked()) {
                assert user != null;
                user.sendEmailVerification()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                                if(user.isEmailVerified())
                                {
                                    Intent intention = new Intent(Validation_Inscription.this, geolocalisation.class);
                                    startActivity(intention);
                                }
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
                for (int i = 0; i < phoneNumber.length()-1; i+=2) {
                    tel.append((String) (list[i] + list[i + 1]));
                    tel.append(" ");
                }
                Log.d(TAG, tel.toString());
                //firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, "123456");
                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Intent intention = new Intent(Validation_Inscription.this, geolocalisation.class);
                        startActivity(intention);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

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

}
