package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.model.Achivment;
import com.example.fitapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView signInText;
    private Button registerButton;
    private EditText editTextUserName, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signInText = findViewById(R.id.RA_signIN);
        registerButton = findViewById(R.id.RA_signInButton);
        editTextUserName = findViewById(R.id.RA_username);
        editTextEmail = findViewById(R.id.RA_email);
        editTextPassword = findViewById(R.id.RA_password);
        progressBar = findViewById(R.id.RA_progressbar);
        signInText.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RA_signIN:
                this.finish();
                break;
            case R.id.RA_signInButton:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String username = editTextUserName.getText().toString().trim();

        if(username.isEmpty()){
            editTextUserName.setError("Username is required");
            editTextUserName.requestFocus();
            return;
        }
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

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("Login", username);
                    userMap.put("Email", email);
                    ArrayList<HashMap<String,Object>> achivments = new ArrayList<>();
                    Achivment a = new Achivment(false, 0);
                    for (int i =0; i< 4; i++){
                        achivments.add(a.getMap());
                    }
                    userMap.put("achivments", achivments);
                    DocumentReference db = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    db.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"User has been registered!",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(RegisterActivity.this,"User has not been registered! Try Again",Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this,"User has not been registered!",Toast.LENGTH_LONG).show();
                }

            }
        });



    }
}