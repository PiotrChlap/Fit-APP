package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.model.Exercise;
import com.example.fitapp.model.HistoryExercise;
import com.example.fitapp.model.HistoryTraining;
import com.example.fitapp.model.Training;
import com.example.fitapp.model.User;
import com.example.fitapp.recycleradapter.TreningRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jakewharton.processphoenix.ProcessPhoenix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class MainScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , TreningRecyclerAdapter.OnCardListener, View.OnClickListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private User user;
    protected RecyclerView mRecyclerView;
    protected TreningRecyclerAdapter mAdapter;
    private ImageView navigationButton;
    private TextView noTrainig;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        user = User.getInstance();
        toolbar = findViewById(R.id.MSA_Toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.MSA_DrawerLayout);
        navigationView = findViewById(R.id.MSA_NavigationView);
        navigationButton = findViewById(R.id.MSA_navigationButton);
        navigationButton.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        noTrainig = findViewById(R.id.MSA_noTraining);
        scrollView = findViewById(R.id.MSA_scroll);
        if(User.getInstance().getTrainings().size() == 0){
            noTrainig.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
            noTrainig.setOnClickListener(this);

        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        View navHeader = navigationView.getHeaderView(0);
        TextView nameUser =  navHeader.findViewById(R.id.NVH_User);
        TextView emailUser =  navHeader.findViewById(R.id.NVH_Email);
        nameUser.setText(user.getUsername());
        emailUser.setText(user.getEmail());
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mRecyclerView = findViewById(R.id.MSA_RecyclerTrening);
        mAdapter = new TreningRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.NAVMN_LogOut:
                FirebaseAuth.getInstance().signOut();
                User.getInstance().delUser();
                ProcessPhoenix.triggerRebirth(this);
                break;
            case R.id.NAVMN_Trening:
                startActivity(new Intent(this, TrainingActivity.class));
                this.finish();
                break;
            case R.id.NAVMN_Achiv:
                startActivity(new Intent(this, AchivActivity.class));
                this.finish();
                break;

            case R.id.NAVMN_Progress:
                getHistoryFirebase();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getHistoryFirebase() {
        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("WorkoutsHistory").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<HistoryTraining> historyTrainings = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ArrayList<HistoryExercise> historyExercises = new ArrayList<>();
                        ArrayList<Object> x = (ArrayList<Object>) document.getData().get("historyExercises");
                        for(Object z : x){
                            HashMap<String, Object> a = (HashMap<String, Object>) z;
                            historyExercises.add(new HistoryExercise(a.get("idExercise").toString(),(ArrayList<String>) a.get("load"),(ArrayList<String>) a.get("series"), a.get("name").toString()));
                        }
                        historyTrainings.add(new HistoryTraining(document.getData().get("name").toString(),((Timestamp) document.getData().get("data")).toDate(), historyExercises));
                    }
                    historyTrainings.sort(Comparator.comparing(HistoryTraining::getData).reversed());
                    user.setHistoryTrainings(historyTrainings);
                } else {
                    Toast.makeText(MainScreenActivity.this,"Bład bazy danych",Toast.LENGTH_LONG).show();
                }

                startActivity(new Intent(MainScreenActivity.this, ProgressActivity.class));
                finish();
            }
        });
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onCardClick(int position) {
        FirebaseFirestore.getInstance().collection("Exercises").whereIn(FieldPath.documentId(), user.getTrainings().get(position).getAllIdExercises()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        for(Exercise ex : user.getTrainings().get(position).getExercises()){
                            if(ex.getIdExercise().equals(document.getId().toString())){
                                ex.updateDetailedDate(document.getData().get("description").toString(),document.getData().get("name").toString(),document.getData().get("ytLink").toString(),document.getData().get("category").toString());
                            break;
                            }
                        }
                    }
                    Intent intent = new Intent(MainScreenActivity.this, StartTrainingActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainScreenActivity.this,"Bład podczas pobierania treningów",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.MSA_navigationButton:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.MSA_noTraining:
                startActivity(new Intent(this, TrainingActivity.class));
                finish();
        }
    }
}