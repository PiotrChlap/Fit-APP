package com.example.fitapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitapp.R;
import com.example.fitapp.model.User;

import org.w3c.dom.Text;


public class DescriptionExerciseFragment extends Fragment {
    private TextView description;
    private int positionTraining;
    private int positionExercise;

    public DescriptionExerciseFragment() {
        // Required empty public constructor
    }


    public static DescriptionExerciseFragment newInstance(int positionExercise, int positionTraining) {
        DescriptionExerciseFragment fragment = new DescriptionExerciseFragment();
        Bundle args = new Bundle();
        args.putInt("positionExercise", positionExercise);
        args.putInt("positionTraining", positionTraining);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
         description = (TextView) getView().findViewById(R.id.DEF_description);
        positionTraining = requireArguments().getInt("positionTraining");
        positionExercise = requireArguments().getInt("positionExercise");
        description.setText(User.getInstance().getTrainings().get(positionTraining).getExercises().get(positionExercise).getDescription());
        description.setMovementMethod(new ScrollingMovementMethod());

    }

}