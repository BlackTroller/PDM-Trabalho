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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {

    EditText nUsername, nEmail, nPassword;
    Button nRegisterBtn;
    TextView nLogin;
    FirebaseAuth fAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nUsername = findViewById(R.id.nUsername);
        nEmail = findViewById(R.id.nEmail);
        nPassword = findViewById(R.id.nPassword);
        nRegisterBtn = findViewById(R.id.nRegister);
        nLogin = findViewById(R.id.nLogin);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        nRegisterBtn.setOnClickListener(v -> {
            String username = nUsername.getText().toString().trim();
            String email = nEmail.getText().toString().trim();
            String password = nPassword.getText().toString().trim();

            if(TextUtils.isEmpty(username)) {
                nUsername.setError("Username is required.");
                return;
            }

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

            //Register User in Firebase

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    user.put("username", username);
                    user.put("email", email);
                    user.put("points", 0);
                    user.put("totalAnswers", 0);
                    user.put("rightAnswers", 0);
                    user.put("wrongAnswers", 0);
                    // Add a new document with a generated ID
                    db.collection("users")
                            .add(user)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                            })
                            .addOnFailureListener(e -> Toast.makeText(Register.this, "Error!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show());



                } else {
                    Toast.makeText(Register.this, "Error!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        nLogin.setOnClickListener(v -> startActivity(new Intent (getApplicationContext(), Login.class)));
    }
}