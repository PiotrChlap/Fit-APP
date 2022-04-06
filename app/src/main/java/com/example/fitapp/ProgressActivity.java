package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fitapp.model.User;
import com.example.fitapp.recycleradapter.ProgressCardRecyclerAdapter;

public class ProgressActivity extends AppCompatActivity implements ProgressCardRecyclerAdapter.OnCardListener, View.OnClickListener {
    protected RecyclerView recyclerView;
    protected ProgressCardRecyclerAdapter progressCardRecyclerAdapter;
    private ImageView backButton;
    private TextView noHistory;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        backButton = findViewById(R.id.PA_backButton);
        noHistory = findViewById(R.id.PA_noHistory);
        scrollView = findViewById(R.id.PA_scroll);
        if(User.getInstance().getHistoryTrainings().size() ==0){
            noHistory.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
        }

        backButton.setOnClickListener(this);


        recyclerView = findViewById(R.id.PA_RecyclerTreningHistory);
        progressCardRecyclerAdapter = new ProgressCardRecyclerAdapter(this);
        recyclerView.setAdapter(progressCardRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainScreenActivity.class));
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.PA_backButton:
                onBackPressed();
                break;

        }
    }
}