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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonForgotPassword;
    private EditText editTextEmail;
    private TextView textViewForgotPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        buttonForgotPassword = findViewById(R.id.FPA_restPasswordButton);
        editTextEmail = findViewById(R.id.FPA_email);
        textViewForgotPassword = findViewById(R.id.FPA_ForgotPassword);
        progressBar = findViewById(R.id.FPA_progressbar);
        mAuth = FirebaseAuth.getInstance();
        textViewForgotPassword.setOnClickListener(this);
        buttonForgotPassword.setOnClickListener(this);
        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {
        String email = editTextEmail.getText().toString().trim();
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
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,"Message has been sent! Check your Email",Toast.LENGTH_LONG).show();
                    finishFunc();

                }else {
                    Toast.makeText(ForgotPasswordActivity.this,"Error has occurred! Try Again!",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void finishFunc(){
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.FPA_ForgotPassword:
                this.finish();
                break;
        }
    }
}