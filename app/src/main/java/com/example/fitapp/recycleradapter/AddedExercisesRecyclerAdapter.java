package com.example.fitapp.recycleradapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.model.Exercise;
import com.example.fitapp.model.Training;
import com.example.fitapp.model.User;

public class AddedExercisesRecyclerAdapter extends RecyclerView.Adapter<AddedExercisesRecyclerAdapter.AddedExerciseViewHolder>{
    private Training editedTraining;
    private OnCardListener onCardListener;

    public AddedExercisesRecyclerAdapter(OnCardListener onCardListener) {
        editedTraining = User.getInstance().getEditedTraining();
        this.onCardListener = onCardListener;
    }

    @NonNull
    @Override
    public AddedExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_card_edit, parent, false);
        return new AddedExerciseViewHolder(view, onCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AddedExerciseViewHolder holder, int position) {
        holder.getNameExercise().setText(editedTraining.getExercises().get(position).getName());
        holder.getSeries().setText(String.valueOf(editedTraining.getExercises().get(position).getSeries().size()));
        holder.getRepeat().setText(editedTraining.getExercises().get(position).getSeriesString());
    }

    public interface OnCardListener{
        void onEditClick(int position);
    }

    @Override
    public int getItemCount() {
        return editedTraining.getExercises().size();
    }

    public void deleteItem(int position) {
        Exercise deletedExercise = editedTraining.getExercises().get(position);
        Integer deletedPosition = position;
        editedTraining.getExercises().remove(position);
        notifyDataSetChanged();

    }

    public static class AddedExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameExercise;
        private TextView series;
        private TextView repeat;
        private ImageView edit;
        private OnCardListener onCardListener;


        public AddedExerciseViewHolder(@NonNull View itemView, OnCardListener onCardListener) {
            super(itemView);
            nameExercise = itemView.findViewById(R.id.ECE_nameExercise);
            series = itemView.findViewById(R.id.ECE_series);
            repeat = itemView.findViewById(R.id.ECE_repeat);
            edit = itemView.findViewById(R.id.ECE_edit);
            edit.setOnClickListener(this);
            this.onCardListener = onCardListener;
        }

        public TextView getNameExercise() {
            return nameExercise;
        }

        public TextView getSeries() {
            return series;
        }

        public TextView getRepeat() {
            return repeat;
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ECE_edit:
                    onCardListener.onEditClick(getAdapterPosition());
                    break;
            }
        }
    }
}
