package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitapp.model.User;
import com.example.fitapp.recycleradapter.ExerciseRecyclerAdapter;

public class StartTrainingActivity extends AppCompatActivity implements View.OnClickListener {
    private int positionTraining;
    private ImageView backButton;
    private TextView nameTraining;
    private Button startTrainingButton;
    protected RecyclerView mRecyclerView;
    protected ExerciseRecyclerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        this.positionTraining = getIntent().getExtras().getInt("position");
        backButton = findViewById(R.id.STA_backButton);
        backButton.setOnClickListener(this);
        startTrainingButton = findViewById(R.id.STA_startTrainingButton);
        startTrainingButton.setOnClickListener(this);
        nameTraining = findViewById(R.id.STA_trainingName);
        nameTraining.setText(User.getInstance().getTrainings().get(positionTraining).getName());
        mRecyclerView = findViewById(R.id.STA_RecyclerExercises);
        mAdapter = new ExerciseRecyclerAdapter(positionTraining);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.STA_backButton:
                onBackPressed();
                break;
            case R.id.STA_startTrainingButton:
                Intent intent = new Intent(this, ExerciseActivity.class);
                intent.putExtra("positionTraining", positionTraining);
                startActivity(intent);
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StartTrainingActivity.this, MainScreenActivity.class));
        this.finish();
    }
}