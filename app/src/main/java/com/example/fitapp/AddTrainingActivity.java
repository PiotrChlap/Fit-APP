package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fitapp.model.Training;
import com.example.fitapp.model.User;
import com.example.fitapp.recycleradapter.AddedExercisesRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.Inet4Address;
import java.util.ArrayList;

public class AddTrainingActivity extends AppCompatActivity implements View.OnClickListener, AddedExercisesRecyclerAdapter.OnCardListener {
    private ImageView backButton;
    private LinearLayout addExerciseButton;
    private int position;
    private EditText nameTraining;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private RecyclerView recyclerView;
    private AddedExercisesRecyclerAdapter adapter;
    private Training editedTraining;
    private Button addTraining;
    private ArrayList<String> days = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        backButton = findViewById(R.id.ATA_backButton);
        addExerciseButton = findViewById(R.id.ATA_addExerciseButton);
        nameTraining = findViewById(R.id.ATA_nameTraining);
        checkBoxes.add(findViewById(R.id.ATA_checkPon));
        checkBoxes.add(findViewById(R.id.ATA_checkWt));
        checkBoxes.add(findViewById(R.id.ATA_checkSr));
        checkBoxes.add(findViewById(R.id.ATA_checkCzw));
        checkBoxes.add(findViewById(R.id.ATA_checkPt));
        checkBoxes.add(findViewById(R.id.ATA_checkSob));
        checkBoxes.add(findViewById(R.id.ATA_checkNd));
        addTraining = findViewById(R.id.ATA_addTrainingButton);

        backButton.setOnClickListener(this);
        addExerciseButton.setOnClickListener(this);
        addTraining.setOnClickListener(this);
        for(CheckBox checkBox: checkBoxes){
            checkBox.setOnClickListener(this);
        }

        nameTraining.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editedTraining.setName(s.toString());
            }
        });
        editedTraining = User.getInstance().getEditedTraining();
        if(User.getInstance().getPositionEditedTraining() != -1){

        } else {
            position = -1;
        }
        nameTraining.setText(editedTraining.getName());
        for(String day : editedTraining.getDays()){
            if(day.compareTo("pon") == 0){
                checkBoxes.get(0).setChecked(true);
                days.add("pon");
            } else if(day.compareTo("wt") == 0) {
                checkBoxes.get(1).setChecked(true);
                days.add("wt");
            }else if(day.compareTo("sr") == 0) {
                checkBoxes.get(2).setChecked(true);
                days.add("sr");
            }else if(day.compareTo("czw") == 0) {
                checkBoxes.get(3).setChecked(true);
                days.add("czw");
            }else if(day.compareTo("pt") == 0) {
                checkBoxes.get(4).setChecked(true);
                days.add("pt");
            }else if(day.compareTo("sob") == 0) {
                checkBoxes.get(5).setChecked(true);
                days.add("sob");
            }else if(day.compareTo("nd") == 0){
                checkBoxes.get(6).setChecked(true);
                days.add("nd");
            }
        }

        recyclerView = findViewById(R.id.ATA_RecyclerExercises);
        adapter = new AddedExercisesRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper =  new ItemTouchHelper(new SwipeToDeleteExerciseCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddTrainingActivity.this, TrainingActivity.class));
        User.getInstance().setEditedTraining(null);
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ATA_backButton:
                onBackPressed();
                break;
            case R.id.ATA_addExerciseButton:
                editedTraining.setDays(days);
                startActivity(new Intent(AddTrainingActivity.this, CategoryActivity.class));
                this.finish();
                break;
            case R.id.ATA_addTrainingButton:
                editedTraining.setDays(days);
                if(editedTraining.getName().isEmpty()){
                    Toast.makeText(AddTrainingActivity.this,"Należy wpisać nazwę treningu!",Toast.LENGTH_LONG).show();
                } else {
                    if(editedTraining.getExercises().size() == 0){
                        Toast.makeText(AddTrainingActivity.this,"Należy dodać minimum jedno ćwiczenie!",Toast.LENGTH_LONG).show();
                    } else {
                        if(editedTraining.getDays().size() == 0){
                            Toast.makeText(AddTrainingActivity.this,"Należy wybrać przynajmniej jeden dzień treningowy!",Toast.LENGTH_LONG).show();
                        } else {
                            if(User.getInstance().getPositionEditedTraining() != -1){
                                updateTrainingFirebase();
                            } else {
                                addTrainingFirebase();
                            }
                        }
                    }
                }

                break;
            case R.id.ATA_checkPon:
                 if(checkBoxes.get(0).isChecked()){
                     days.add("pon");
                 } else {
                     for(int i =0; i< days.size(); i++){
                         if(days.get(i).equals("pon")){
                             days.remove(days.indexOf(days.get(i)));
                         }
                     }
                 }
                break;
            case R.id.ATA_checkWt:
                if(checkBoxes.get(1).isChecked()){
                    days.add("wt");
                } else {
                    for(int i =0; i< days.size(); i++){
                        if(days.get(i).equals("wt")){
                            days.remove(days.indexOf(days.get(i)));
                        }
                    }
                }
                break;
            case R.id.ATA_checkSr:
                if(checkBoxes.get(2).isChecked()){
                    days.add("sr");
                } else {
                    for(int i =0; i< days.size(); i++){
                        if(days.get(i).equals("sr")){
                            days.remove(days.indexOf(days.get(i)));
                        }
                    }
                }
                break;
            case R.id.ATA_checkCzw:
                if(checkBoxes.get(3).isChecked()){
                    days.add("czw");
                } else {
                    for(int i =0; i< days.size(); i++){
                        if(days.get(i).equals("czw")){
                            days.remove(days.indexOf(days.get(i)));
                        }
                    }
                }
                break;
            case R.id.ATA_checkPt:
                if(checkBoxes.get(4).isChecked()){
                    days.add("pt");
                } else {
                    for(int i =0; i< days.size(); i++){
                        if(days.get(i).equals("pt")){
                            days.remove(days.indexOf(days.get(i)));
                        }
                    }
                }
                break;
            case R.id.ATA_checkSob:
                if(checkBoxes.get(5).isChecked()){
                    days.add("sob");
                } else {
                    for(int i =0; i< days.size(); i++){
                        if(days.get(i).equals("sob")){
                            days.remove(days.indexOf(days.get(i)));
                        }
                    }
                }
                break;
            case R.id.ATA_checkNd:
                if(checkBoxes.get(6).isChecked()){
                    days.add("nd");
                } else {
                    for(int i =0; i< days.size(); i++){
                        if(days.get(i).equals("nd")){
                            days.remove(days.indexOf(days.get(i)));
                        }
                    }
                }
                break;


        }
    }

    private void addTrainingFirebase(){
        if (!User.getInstance().getAchivments().get(2).getAdded()){
            User.getInstance().getAchivments().get(2).addAchiv();
            FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid()).update("achivments", User.getInstance().getAchivmentsHasmap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("FireBase", "Dodano osiągnięcie Mocny początek!");
                    Toast.makeText(AddTrainingActivity.this,"Dodano osiągnięcie Mocny początek!",Toast.LENGTH_LONG).show();
                }
            });
        }

        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Workouts")
                .add(editedTraining.getMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        editedTraining.setIdTraining(documentReference.getId().toString());
                        for(int i = 0; i< editedTraining.getDays().size(); i++){
                            Training training = editedTraining.getDeepCopy();
                            training.setDay(days.get(i));
                            User.getInstance().getTrainings().add(training);
                        }
                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FireBase", "Dodawanie treningu nie powiodło się!");
                    }
                });
    }

    private void updateTrainingFirebase() {
        User.getInstance().deleteTraining(editedTraining.getIdTraining());
        for(int i = 0; i< editedTraining.getDays().size(); i++){
            Training training = editedTraining.getDeepCopy();
            training.setDay(days.get(i));
            User.getInstance().getTrainings().add(training);
        }
        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid()).collection("Workouts")
                .document(editedTraining.getIdTraining()).update(editedTraining.getMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("FireBase", "Trening dodano!");
                onBackPressed();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("FireBase", "Dodawanie treningu nie powiodło się!");
            }
        });
    }


    @Override
    public void onEditClick(int position) {
        Intent intent = new Intent(this, ExerciseCreatorActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
        this.finish();
    }
}