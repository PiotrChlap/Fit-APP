package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.model.Exercise;
import com.example.fitapp.model.ReadySetsSingleton;
import com.example.fitapp.model.Training;
import com.example.fitapp.model.User;
import com.example.fitapp.recycleradapter.DayNameTrainingRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TrainingActivity extends AppCompatActivity implements DayNameTrainingRecyclerAdapter.OnCardListener, View.OnClickListener{
    protected RecyclerView mRecyclerView;
    protected DayNameTrainingRecyclerAdapter mAdapter;
    private TextView noTraining;
    private ImageView homeButton;
    private ImageView addTrainingView;
    private Button readySetsButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colRef = db.collection("ReadySets");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        mRecyclerView = findViewById(R.id.TA_daysTrainingRecyclerAdapter);
        noTraining = findViewById(R.id.TA_noTraining);
        if(User.getInstance().getTrainings().size() == 0){
            noTraining.setVisibility(View.VISIBLE);
        }

        mAdapter = new DayNameTrainingRecyclerAdapter( this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        homeButton = findViewById(R.id.TA_backButton);
        homeButton.setOnClickListener(this);
        addTrainingView = findViewById(R.id.TA_addTrainingButton);
        addTrainingView.setOnClickListener(this);
        readySetsButton = findViewById(R.id.TA_startTrainingButton);
        ArrayList<Training> trainings_12 = new ArrayList<>();
        readySetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadySetsSingleton.getInstance().getTrainings().clear();
                colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                for(int i =0; i<((ArrayList<String>) document.getData().get("days")).size();i++){
                                    ArrayList<Exercise> workout = new ArrayList<>();
                                    for(HashMap<String,Object> exercise : (ArrayList<HashMap<String,Object>>) document.getData().get("exercise") ){
                                        workout.add(new Exercise(exercise.get("name").toString(), (ArrayList<String>) exercise.get("series")));
                                    }
                                    trainings_12.add(new Training(document.getData().get("name").toString(), workout, ((ArrayList<String>)document.getData().get("days")).get(i), document.getId(), (ArrayList<String>) document.getData().get("days")));
                                    int xd =1 ;
                                }
                            }
                            ReadySetsSingleton readySetsSingleton = ReadySetsSingleton.getInstance();
                            readySetsSingleton.setTrainings(trainings_12);
                            startActivity(new Intent(TrainingActivity.this, TrainingReadySetsActivity.class));
                            finish();
                        }
                    }
                });

            }
        });

    }

    @Override
    public void onCardClick(int position) {
        FirebaseFirestore.getInstance().collection("Exercises").whereIn(FieldPath.documentId(), User.getInstance().getTrainings().get(position).getAllIdExercises()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        for(Exercise ex : User.getInstance().getTrainings().get(position).getExercises()){
                            if(ex.getIdExercise().equals(document.getId().toString())){
                                ex.updateDetailedDate(document.getData().get("description").toString(),document.getData().get("name").toString(),document.getData().get("ytLink").toString(),document.getData().get("category").toString());
                                break;
                            }
                        }
                    }
                } else {
                    Toast.makeText(TrainingActivity.this,"Bład podczas pobierania treningów",Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(TrainingActivity.this, AddTrainingActivity.class);
                User.getInstance().setPositionEditedTraining(position);
                User.getInstance().setEditedTraining(User.getInstance().getTrainings().get(position).getDeepCopy());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void del(int position) {
        mAdapter.deleteItem(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.TA_backButton:
                onBackPressed();
                break;
            case R.id.TA_addTrainingButton:
                User.getInstance().setPositionEditedTraining(-1);
                User.getInstance().setEditedTraining(new Training("", new ArrayList<>(),"pon", null, new ArrayList<String>()));
                startActivity(new Intent(TrainingActivity.this, AddTrainingActivity.class));
                finish();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TrainingActivity.this, MainScreenActivity.class));
        this.finish();
    }
}