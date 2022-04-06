package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView register,forgotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIN;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button tmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = findViewById(R.id.LA_register);
        forgotPassword = findViewById(R.id.LA_forgot_password);
        editTextEmail = findViewById(R.id.LA_email);
        editTextPassword = findViewById(R.id.LA_password);
        buttonSignIN = findViewById(R.id.LA_signInButton);
        progressBar = findViewById(R.id.LA_progressbar);
        mAuth = FirebaseAuth.getInstance();
        buttonSignIN.setOnClickListener(this);
        register.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.LA_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.LA_signInButton:
                signIn();
                break;
            case R.id.LA_forgot_password:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }

    private void signIn() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a correct email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 7){
            editTextPassword.setError("Min password length is 7");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
//                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                    if(firebaseUser.isEmailVerified()){
//                        startActivity(new Intent(LoginActivity.this, MainScreenActivity.class));
//                    }else{
//                        firebaseUser.sendEmailVerification();
//                        Toast.makeText(LoginActivity.this,"Check your email to verify your account",Toast.LENGTH_LONG).show();
//                    }

                }else{
                    Toast.makeText(LoginActivity.this,"Failed to login !",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}