package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.fitapp.model.Exercise;
import com.example.fitapp.model.User;
import com.example.fitapp.model.WorkoutsWikipedia;
import com.example.fitapp.recycleradapter.AddExerciseRecyclerAdapter;

public class AddExerciseActivity extends AppCompatActivity implements AddExerciseRecyclerAdapter.OnCardListener {
    private RecyclerView recyclerView;
    private AddExerciseRecyclerAdapter adapter;
    private String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        category = getIntent().getExtras().getString("category");
        recyclerView = findViewById(R.id.AEA_ExerciseRecyclerView);
        adapter = new AddExerciseRecyclerAdapter(this,category);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddExerciseActivity.this, CategoryActivity.class));
        this.finish();
    }

    @Override
    public void onCardClick(int position) {
        Exercise ex = WorkoutsWikipedia.getInstance().getExerciseCategory(category).get(position).createNewExerciseTemplateFromThis();
        User.getInstance().getEditedTraining().addExercise(ex);
        Intent intent = new Intent(AddExerciseActivity.this, ExerciseCreatorActivity.class);
        intent.putExtra("position", User.getInstance().getEditedTraining().getExercises().indexOf(ex));
        startActivity(intent);
        this.finish();
    }

    @Override
    public void infoExercise(int position) {
        Intent intent = new Intent(AddExerciseActivity.this, InfoExerciseActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}