package com.nessy.islamapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPass;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        btnRegister = findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        btnRegister.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                //set error
                etEmail.setError("Invalid Email");
                etEmail.setFocusable(true);
            } else if (pass.length() < 8) {
                etPass.setError("Password length at least 8 characters");
                etPass.setFocusable(true);
            } else {
                registerUser(email, pass);
            }
        });
    }

    private void registerUser(String email, String pass) {
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        progressBar.setVisibility(View.GONE);
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(RegisterActivity.this, "Registered...\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        // If sign in fails
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }

                    // ...
                }).addOnFailureListener(e -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }
}