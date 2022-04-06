package com.example.fitapp.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitapp.ExerciseActivity;
import com.example.fitapp.R;
import com.example.fitapp.model.User;


public class SerieFragment extends Fragment implements View.OnClickListener{
    private int positionTraining;
    private int positionExercise;
    private TextView serieNumber;
    private EditText loadNumber;
    private EditText repeatNumber;
    private int serie;
    private User user;
    private ImageView checkButton;
    private TextView serieText;

    public SerieFragment() {
        // Required empty public constructor
    }


    public static SerieFragment newInstance(int serie, int positionTraining, int positionExercise) {
        SerieFragment fragment = new SerieFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("serie", serie);
        bundle.putInt("positionTraining", positionTraining);
        bundle.putInt("positionExercise", positionExercise);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        positionTraining = requireArguments().getInt("positionTraining");
        positionExercise = requireArguments().getInt("positionExercise");
        serie = requireArguments().getInt("serie");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_serie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        user = User.getInstance();
        serieNumber = getView().findViewById(R.id.FS_numberSeries);
        loadNumber = getView().findViewById(R.id.FS_load);
        checkButton = getView().findViewById(R.id.FS_checkButton);
        checkButton.setOnClickListener(this);
        serieText = getView().findViewById(R.id.FS_serieText);
        loadNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                user.getActuallTraining().getHistoryExercises().get(positionExercise).changeLoadValue(serie, s.toString());
            }
        });
        repeatNumber = getView().findViewById(R.id.FS_numberRepeat);
        repeatNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                user.getActuallTraining().getHistoryExercises().get(positionExercise).changeRepeatValue(serie, s.toString());
            }
        });
        serieNumber.setText(String.valueOf(serie+1));
        repeatNumber.setText(user.getTrainings().get(positionTraining).getExercises().get(positionExercise).getSeries().get(serie));
        loadNumber.setText(user.getTrainings().get(positionTraining).getExercises().get(positionExercise).getLoad().get(serie));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.FS_checkButton:
                serieText.setText("Ukończono ");
                loadNumber.setEnabled(false);
                loadNumber.setBackgroundColor(Color.TRANSPARENT);
                repeatNumber.setEnabled(false);
                repeatNumber.setBackgroundColor(Color.TRANSPARENT);
                checkButton.setVisibility(View.GONE);
                user.getActuallTraining().getHistoryExercises().get(positionExercise).changeCheckingValue(serie, true);
                ExerciseActivity.myBundle.putString("clicked", "1");

        }
    }
}