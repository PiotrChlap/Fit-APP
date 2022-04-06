package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitapp.model.Exercise;
import com.example.fitapp.model.ReadySetsSingleton;
import com.example.fitapp.model.Training;
import com.example.fitapp.model.User;
import com.example.fitapp.recycleradapter.DayNameTrainingRecyclerAdapter;
import com.example.fitapp.recycleradapter.ReadySetsTrainingRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TrainingReadySetsActivity extends AppCompatActivity implements ReadySetsTrainingRecyclerAdapter.OnCardListenerMy, View.OnClickListener{

    protected RecyclerView mRecyclerView;
    protected ReadySetsTrainingRecyclerAdapter mAdapter;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_ready_sets);
        mRecyclerView = findViewById(R.id.ATR_daysTrainingRecyclerAdapter);
        mAdapter = new ReadySetsTrainingRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton = findViewById(R.id.ATR_backButton);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ATR_backButton:
                onBackPressed();
                break;
        }

    }




    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TrainingActivity.class));
        this.finish();
    }

    @Override
    public void onCardClick(int position) {
        FirebaseFirestore.getInstance().collection("Exercises").whereIn(FieldPath.documentId(), ReadySetsSingleton.getInstance().getTrainings().get(position).getAllIdExercises()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        for(Exercise ex : ReadySetsSingleton.getInstance().getTrainings().get(position).getExercises()){
                            if(ex.getIdExercise().equals(document.getId().toString())){
                                ex.updateDetailedDate(document.getData().get("description").toString(),document.getData().get("name").toString(),document.getData().get("ytLink").toString(),document.getData().get("category").toString());
                                break;
                            }
                        }
                    }
                } else {
                    Toast.makeText(TrainingReadySetsActivity.this,"Bład podczas pobierania treningów",Toast.LENGTH_LONG).show();
                }
                User.getInstance().setEditedTraining(ReadySetsSingleton.getInstance().getTrainings().get(position).getDeepCopy());
                Intent intent = new Intent(TrainingReadySetsActivity.this, AddTrainingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}