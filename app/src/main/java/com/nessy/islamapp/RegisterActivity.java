package com.nessy.islamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText etEmail,etPass;
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

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                //set error
                etEmail.setError("Invalid Email");
                etEmail.setFocusable(true);
            }else if (pass.length()<8){
                etPass.setError("Password length at least 8 characters");
                etPass.setFocusable(true);
            }else {
                registerUser(email, pass);
            }
        });
    }

    private void registerUser(String email, String pass) {

    }
}