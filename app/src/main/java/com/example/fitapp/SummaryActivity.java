package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.model.User;
import com.example.fitapp.recycleradapter.SumExerciseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener{
    protected RecyclerView mRecyclerView;
    protected SumExerciseRecyclerAdapter mAdapter;
    private ImageView backButton;
    private int position;
    private TextView nameTraining;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            int hours = User.getInstance().getActuallTraining().getData().getHours();
            if (hours >=22 || hours <= 6){
                if (!User.getInstance().getAchivments().get(0).getAdded()){
                    User.getInstance().getAchivments().get(0).addAchiv();
                    Log.i("FireBase", "Dodano osiągnięcie Nocny siłacz!");
                    Toast.makeText(SummaryActivity.this,"Dodano osiągnięcie Nocny siłacz!",Toast.LENGTH_LONG).show();

                }
            }
            if(User.getInstance().getAchivments().get(3).getAdded()){
                User.getInstance().getAchivments().get(3).riseAmount();
                if(User.getInstance().getAchivments().get(3).getAmount() == 100){
                    User.getInstance().getAchivments().get(3).addAchiv();
                    Log.i("FireBase", "Dodano osiągnięcie Wytrwały dzik!");
                    Toast.makeText(SummaryActivity.this,"Dodano osiągnięcie Nocny siłacz!",Toast.LENGTH_LONG).show();

                }
            }
            if(!User.getInstance().getAchivments().get(1).getAdded()){
                if (User.getInstance().getActuallTraining().checkAllTrainingChecked()){
                    User.getInstance().getAchivments().get(1).riseAmount();
                    if(User.getInstance().getAchivments().get(1).getAmount() == 10){
                        User.getInstance().getAchivments().get(1).addAchiv();
                        Log.i("FireBase", "Dodano osiągnięcie Trenining Ponad Wszystko!");
                        Toast.makeText(SummaryActivity.this,"Dodano osiągnięcie Trenining Ponad Wszystko!",Toast.LENGTH_LONG).show();
                    }
                }
            }
            FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid()).update("achivments", User.getInstance().getAchivmentsHasmap());

            FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("WorkoutsHistory")
                    .add(User.getInstance().getActuallTraining())
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("FirebaseSend", "DocumentSnapshot written with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FirebaseSendError", "Error adding document", e);
                        }
                    });
            position = -1;
        } else {
            position = extras.getInt("position");
            User.getInstance().setActuallTraining(User.getInstance().getHistoryTrainings().get(position));
        }
        nameTraining = findViewById(R.id.SA_nameTraining);
        nameTraining.setText(User.getInstance().getActuallTraining().getName());
        mRecyclerView = findViewById(R.id.SA_recyclerSumExercise);
        mAdapter = new SumExerciseRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton = findViewById(R.id.SA_backButton);
        backButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SA_backButton:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(position == -1){
            startActivity(new Intent(SummaryActivity.this, MainScreenActivity.class));
        }else {
            startActivity(new Intent(SummaryActivity.this, ProgressActivity.class));
        }
        finish();
    }
}