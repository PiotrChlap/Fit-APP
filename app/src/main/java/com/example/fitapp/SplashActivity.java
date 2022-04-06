package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitapp.model.Achivment;
import com.example.fitapp.model.Exercise;
import com.example.fitapp.model.Training;
import com.example.fitapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        logo = findViewById(R.id.AS_logo);
        Animation anim = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.bounce);
        logo.startAnimation(anim);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            User user = User.getInstance();
                            DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            user.init(document.getData().get("Login").toString(), document.getData().get("Email").toString());
                                            ArrayList<HashMap<String,Object>> x = (ArrayList<HashMap<String, Object>>) document.getData().get("achivments");
                                            ArrayList<Achivment> achivments = new ArrayList<>();
                                            for (HashMap<String,Object> a : x){
                                                achivments.add(new Achivment(Boolean.valueOf(a.get("added").toString()), Integer.valueOf(a.get("amount").toString())));
                                            }
                                            user.setAchivments(achivments);
                                            extracted(user);

                                        } else {
                                            Toast.makeText(SplashActivity.this,"Bład w bazie danych",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                                        }
                                    } else {
                                        Toast.makeText(SplashActivity.this,"failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }else {
                            startActivity(new Intent(SplashActivity.this,LoginActivity.class));

                        }

                    }
                });

            }
        },4000);
    }

    private void extracted(User user) {
        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Workouts").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        for(int i =0; i<((ArrayList<String>) document.getData().get("days")).size();i++){
                            ArrayList<Exercise> workout = new ArrayList<>();
                            for(HashMap<String,Object> exercise : (ArrayList<HashMap<String,Object>>) document.getData().get("exercises") ){
                                workout.add(new Exercise(exercise.get("name").toString(), (ArrayList<String>) exercise.get("series")));
                            }
                            user.addTraning(new Training(document.getData().get("name").toString(), workout, ((ArrayList<String>)document.getData().get("days")).get(i), document.getId(), (ArrayList<String>) document.getData().get("days")));
                        }
                    }
                } else {
                    Toast.makeText(SplashActivity.this,"Bład podczas pobierania treningów",Toast.LENGTH_LONG).show();
                }

                startActivity(new Intent(SplashActivity.this, MainScreenActivity.class));
            }
        });
    }
}