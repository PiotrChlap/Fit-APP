package com.example.fitapp.recycleradapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.model.Exercise;
import com.example.fitapp.model.User;

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ExerciseViewHolder> {
    private User user;
    private int selectedTraning;

    public ExerciseRecyclerAdapter(int position) {
        this.user = User.getInstance();
        this.selectedTraning = position;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.exercise_card, viewGroup, false);

        return new ExerciseRecyclerAdapter.ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = user.getTrainings().get(this.selectedTraning).getExercises().get(position);
        holder.getNameExercise().setText(exercise.getName());
        holder.getSeries().setText(String.valueOf(exercise.getSeries().size()));
        holder.getRepeat().setText(exercise.getSeriesString());
    }

    @Override
    public int getItemCount() {
        return user.getTrainings().get(selectedTraning).getExercises().size();
    }


    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private TextView series;
        private TextView repeat;
        private TextView nameExercise;
        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            series = itemView.findViewById(R.id.EC_series);
            repeat = itemView.findViewById(R.id.EC_repeat);
            nameExercise = itemView.findViewById(R.id.EC_nameExercise);
        }

        public TextView getSeries() {
            return series;
        }

        public TextView getRepeat() {
            return repeat;
        }

        public TextView getNameExercise() {
            return nameExercise;
        }
    }
}
