package com.example.fitapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Looper;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitapp.ExerciseActivity;
import com.example.fitapp.R;
import com.example.fitapp.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExerciseFragment extends Fragment implements View.OnClickListener {
    private int positionTraining;
    private int positionExercise;
    private int serie = 0;
    private TextView exerciseName;
    private ImageView previousSerie;
    public ImageView nextSerie;
    private ArrayList<SerieFragment> series = new ArrayList<>();


    public ExerciseFragment() {
        // Required empty public constructor
    }


    public static ExerciseFragment newInstance(int positionExercise, int positionTraining) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putInt("positionTraining", positionTraining);
        args.putInt("positionExercise", positionExercise);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        positionTraining = requireArguments().getInt("positionTraining");
        positionExercise = requireArguments().getInt("positionExercise");
        for (int i = 0; i < User.getInstance().getTrainings().get(positionTraining).getExercises().get(positionExercise).getSeries().size(); i++){
            series.add(SerieFragment.newInstance(i,positionTraining,positionExercise));
        }
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("positionTraining", positionTraining);
            bundle.putInt("positionExercise", positionExercise);
            bundle.putInt("serie", serie);
            getChildFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .add(R.id.FE_serieFragment, series.get(0))
                    .commit();
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exerciseName = getView().findViewById(R.id.FE_nameExercise);
        exerciseName.setText(User.getInstance().getTrainings().get(positionTraining).getExercises().get(positionExercise).getName());
        previousSerie = view.findViewById(R.id.FE_previousSerie);
        nextSerie = view.findViewById(R.id.FE_nextSerie);
        previousSerie.setOnClickListener(this);
        nextSerie.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }




    @Override
    public void onClick(View v) {
        FragmentTransaction  ft = getChildFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.FE_previousSerie:
                serie--;
                for (SerieFragment f : series){
                    if(f.isAdded()){
                        ft.hide(f);
                    }
                }
                if (series.get(serie).isAdded()){
                    ft.show(series.get(serie));
                } else {
                    ft.add(R.id.FE_serieFragment, series.get(serie), String.valueOf(serie));
                }
                if (serie == 0){
                    previousSerie.setVisibility(View.INVISIBLE);
                }
                ExerciseActivity.myBundle.putString("changed", "1");

                break;
            case R.id.FE_nextSerie:
                if (series.size()-1 > serie ){
                    previousSerie.setVisibility(View.VISIBLE);
                    serie++;
                    for (SerieFragment f : series){
                        if(f.isAdded()){
                            ft.hide(f);
                        }
                    }
                    if (series.get(serie).isAdded()){
                        ft.show(series.get(serie));
                    } else {
                        ft.add(R.id.FE_serieFragment, series.get(serie), String.valueOf(serie));
                    }
                }
                ExerciseActivity.myBundle.putString("changed", "1");
                break;
        }
        ft.commit();
    }
}