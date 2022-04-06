package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitapp.model.User;
import com.example.fitapp.recycleradapter.SerieRecyclerAdapter;

public class ExerciseCreatorActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView nameExercise;
    private RecyclerView recyclerView;
    private SerieRecyclerAdapter adapter;
    private int position;
    private CardView addSerieButton;
    private Button addExercise;
    private ImageView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_creator);
        position = getIntent().getExtras().getInt("position");
        nameExercise = findViewById(R.id.ECA_exerciseName);
        addSerieButton = findViewById(R.id.ECA_addSerieButton);
        addExercise = findViewById(R.id.ECA_addExerciseButton);
        info = findViewById(R.id.ECA_info);

        info.setOnClickListener(this);
        addExercise.setOnClickListener(this);
        addSerieButton.setOnClickListener(this);
        nameExercise.setText(User.getInstance().getEditedTraining().getExercises().get(position).getName());



        recyclerView = findViewById(R.id.ECA_RecyclerSeries);
        adapter = new SerieRecyclerAdapter(position);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteSerieCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ExerciseCreatorActivity.this, CategoryActivity.class));
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ECA_addSerieButton:
                User.getInstance().getEditedTraining().getExercises().get(position).addSeries("10", "10");
                adapter.notifyItemInserted(  User.getInstance().getEditedTraining().getExercises().get(position).getSeries().size()-1);
                break;
            case R.id.ECA_addExerciseButton:
                startActivity(new Intent(ExerciseCreatorActivity.this, AddTrainingActivity.class));
                this.finish();
                break;
            case R.id.ECA_info:
                Intent intent = new Intent(ExerciseCreatorActivity.this, InfoExerciseActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("x", "xx");
                startActivity(intent);
                break;
        }
    }
}