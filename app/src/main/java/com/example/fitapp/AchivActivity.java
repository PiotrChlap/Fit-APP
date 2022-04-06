package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.model.Achivment;
import com.example.fitapp.model.User;

import java.util.ArrayList;

public class AchivActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView backPress;
    private ArrayList<ImageView> achivsImg = new ArrayList<>();
    private ArrayList<TextView> achivsText = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achiv);
        achivsImg.add(findViewById(R.id.AA_nightAchivImg));
        achivsImg.add(findViewById(R.id.AA_AllAchivImg));
        achivsImg.add(findViewById(R.id.AA_startAchivImg));
        achivsImg.add(findViewById(R.id.AA_boarAchivImg));

        achivsText.add(findViewById(R.id.AA_nightAchivText));
        achivsText.add(findViewById(R.id.AA_AllAchivText));
        achivsText.add(findViewById(R.id.AA_startAchivText));
        achivsText.add(findViewById(R.id.AA_boarAchivText));


        for (int i = 0 ; i< achivsImg.size(); i++){
            achivsImg.get(i).setColorFilter(getColor(R.color.grey_out));
            achivsImg.get(i).setOnClickListener(this);
        }
        ArrayList<Achivment> achivments = User.getInstance().getAchivments();

        for (int i = 0; i<achivments.size();i++){
            if(achivments.get(i).getAdded()){
                achivsImg.get(i).clearColorFilter();
                achivsText.get(i).setTextColor(getColor(R.color.white));
            }
        }


        backPress = findViewById(R.id.AA_backButton);
        backPress.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainScreenActivity.class));
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.AA_backButton:
               onBackPressed();
                break;
            case R.id.AA_nightAchivImg:
                Toast.makeText(AchivActivity.this,"Wykonaj trening pomiędzy 22:00-6:00",Toast.LENGTH_LONG).show();
                break;
            case R.id.AA_AllAchivImg:
                Toast.makeText(AchivActivity.this,"Wykonaj 10 treningów bez pominięcia żadnej serii",Toast.LENGTH_LONG).show();
                break;
            case R.id.AA_startAchivImg:
                Toast.makeText(AchivActivity.this,"Dodaj pierwszy trening",Toast.LENGTH_LONG).show();
                break;
            case R.id.AA_boarAchivImg:
                Toast.makeText(AchivActivity.this,"Wykonaj 100 treningów",Toast.LENGTH_LONG).show();
                break;
        }
    }
}