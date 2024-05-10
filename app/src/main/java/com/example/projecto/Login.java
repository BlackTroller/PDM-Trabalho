package com.example.projecto;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText nEmail, nPassword;
    Button nLoginBtn;
    TextView nRegister;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nEmail = findViewById(R.id.nEmail);
        nPassword = findViewById(R.id.nPassword);
        nLoginBtn = findViewById(R.id.nLogin);
        nRegister = findViewById(R.id.nRegister);

        fAuth = FirebaseAuth.getInstance();

        nLoginBtn.setOnClickListener(v -> {

            String email = nEmail.getText().toString().trim();
            String password = nPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email)) {
                nEmail.setError("Email is required.");
                return;
            }

            if(TextUtils.isEmpty(password)) {
                nPassword.setError("Password is required.");
                return;
            }

            if(password.length() < 8 ) {
                nPassword.setError("Password must be >= 8 characters.");
                return;
            }

            //Login user

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(Login.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(Login.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        });

        nRegister.setOnClickListener(v -> startActivity(new Intent (getApplicationContext(), Register.class)));

    }
}