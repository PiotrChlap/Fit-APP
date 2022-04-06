package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fitapp.model.Exercise;
import com.example.fitapp.model.User;
import com.example.fitapp.model.WorkoutsWikipedia;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView homeButton;
    private Button backButton;
    private Button hoodsButton;
    private Button shoulderButton;
    private Button stomachButton;
    private Button chestButton;
    private Button quadricepsButton;
    private Button forearmsButton;
    private Button calvesButton;
    private Button bicepsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        homeButton = findViewById(R.id.CA_homeButton);
        backButton = findViewById(R.id.CA_backButton);
        hoodsButton = findViewById(R.id.CA_hoodsButton);
        shoulderButton = findViewById(R.id.CA_shoulderButton);
        stomachButton = findViewById(R.id.CA_stomachButton);
        chestButton = findViewById(R.id.CA_chestButton);
        quadricepsButton = findViewById(R.id.CA_quadricepsButton);
        forearmsButton = findViewById(R.id.CA_forearmsButton);
        calvesButton = findViewById(R.id.CA_calvesButton);
        bicepsButton = findViewById(R.id.CA_bicepsButton);

        homeButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        hoodsButton.setOnClickListener(this);
        shoulderButton.setOnClickListener(this);
        stomachButton.setOnClickListener(this);
        chestButton.setOnClickListener(this);
        quadricepsButton.setOnClickListener(this);
        forearmsButton.setOnClickListener(this);
        calvesButton.setOnClickListener(this);
        bicepsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.CA_homeButton:
                startActivity(new Intent(CategoryActivity.this, AddTrainingActivity.class));
                this.finish();
                break;
            case R.id.CA_backButton:
                getExerciseFromFirebase(getString(R.string.category_back));
                break;
            case R.id.CA_hoodsButton:
                getExerciseFromFirebase(getString(R.string.category_hoods));
                break;
            case R.id.CA_shoulderButton:
                getExerciseFromFirebase(getString(R.string.category_shoulder));
                break;
            case R.id.CA_stomachButton:
                getExerciseFromFirebase(getString(R.string.category_stomach));
                break;
            case R.id.CA_chestButton:
                getExerciseFromFirebase(getString(R.string.category_chest));
                break;
            case R.id.CA_quadricepsButton:
                getExerciseFromFirebase(getString(R.string.category_quadriceps));
                break;
            case R.id.CA_forearmsButton:
                getExerciseFromFirebase(getString(R.string.category_forearms));
                break;
            case R.id.CA_calvesButton:
                getExerciseFromFirebase(getString(R.string.category_calves));
                break;
            case R.id.CA_bicepsButton:
                getExerciseFromFirebase(getString(R.string.category_biceps));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CategoryActivity.this, AddTrainingActivity.class));
        this.finish();
    }

    public void getExerciseFromFirebase(String category){
        FirebaseFirestore.getInstance().collection("Exercises")
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Exercise> exercises = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                exercises.add(new Exercise(document.getData().get("category").toString(),
                                        document.getData().get("name").toString(),
                                        document.getData().get("ytLink").toString(),
                                        document.getData().get("description").toString(),
                                        document.getId()));
                            }
                            WorkoutsWikipedia.getInstance().addArrayExercises(category, exercises);
                            Intent intent = new Intent(CategoryActivity.this, AddExerciseActivity.class);
                            intent.putExtra("category", category);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d("Firebase", "Error getting documents: ", task.getException());
                        }

                    }
                });
    }

}