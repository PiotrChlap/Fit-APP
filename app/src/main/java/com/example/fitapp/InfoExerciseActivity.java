package com.example.fitapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.model.User;
import com.example.fitapp.model.WorkoutsWikipedia;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class InfoExerciseActivity extends YouTubeBaseActivity implements View.OnClickListener {
    private String api_key = "AIzaSyATVrDemeSRkUmEiO_wmeUmQfj8mYlxH4Q";
    private ImageView backButton;
    private TextView nameExercise;
    private TextView description;
    private int position;
    private String ytLink;
    private String category;
    private int positionTraining;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        position = getIntent().getExtras().getInt("position");
        setContentView(R.layout.activity_info_exercise);
        YouTubePlayerView ytPlayer = (YouTubePlayerView)findViewById(R.id.AIE_ytPlayer);
        backButton = findViewById(R.id.AIE_backButton);
        nameExercise = findViewById(R.id.AIE_exerciseName);
        description = findViewById(R.id.AIE_description);
        description.setMovementMethod(new ScrollingMovementMethod());
        nameExercise.setMovementMethod(new ScrollingMovementMethod());
        backButton.setOnClickListener(this);
        if(getIntent().getExtras().getString("x") == null){
            category = getIntent().getExtras().getString("category");
            nameExercise.setText( WorkoutsWikipedia.getInstance().getExerciseCategory(category).get(position).getName());
            description.setText( WorkoutsWikipedia.getInstance().getExerciseCategory(category).get(position).getDescription());
            String[] tmp = WorkoutsWikipedia.getInstance().getExerciseCategory(category).get(position).getYtLink().split("=",2);
            ytLink = tmp[1];
        }else if(getIntent().getExtras().getString("x").equals("zzz")){
            positionTraining = getIntent().getExtras().getInt("positionTraining");
            nameExercise.setText(User.getInstance().getTrainings().get(positionTraining).getExercises().get(position).getName());
            description.setText( User.getInstance().getTrainings().get(positionTraining).getExercises().get(position).getDescription());
            String[] tmp = User.getInstance().getTrainings().get(positionTraining).getExercises().get(position).getYtLink().split("=",2);
            ytLink = tmp[1];
        }else {
            nameExercise.setText(User.getInstance().getEditedTraining().getExercises().get(position).getName());
            description.setText( User.getInstance().getEditedTraining().getExercises().get(position).getDescription());
            String[] tmp = User.getInstance().getEditedTraining().getExercises().get(position).getYtLink().split("=",2);
            ytLink = tmp[1];
        }
        ytPlayer.initialize(
                api_key,
                new YouTubePlayer.OnInitializedListener() {

                    @Override
                    public void onInitializationSuccess(
                            YouTubePlayer.Provider provider,
                            YouTubePlayer youTubePlayer, boolean b)
                    {
                        youTubePlayer.loadVideo(ytLink);
                        youTubePlayer.play();
                    }


                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult
                                                                youTubeInitializationResult)
                    {
                        Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.AIE_backButton:
                onBackPressed();
                break;
        }
    }
}