package com.example.fitapp.recycleradapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.model.Exercise;
import com.example.fitapp.model.User;
import com.google.android.material.snackbar.Snackbar;

public class SerieRecyclerAdapter extends RecyclerView.Adapter<SerieRecyclerAdapter.SerieViewHolder> {
    private int position;
    private Exercise exercise;

    public SerieRecyclerAdapter(int position) {
        this.position = position;
        this.exercise = User.getInstance().getEditedTraining().getExercises().get(this.position);
    }

    @NonNull
    @Override
    public SerieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serie_card, parent, false);
        return new SerieViewHolder(view, position);
    }

    @Override
    public void onBindViewHolder(@NonNull SerieViewHolder holder, int position) {
        holder.getSerieNumber().setText(String.valueOf(position+1));
        holder.getRepeat().setText(exercise.getSeries().get(position));
        holder.getLoad().setText(exercise.getLoad().get(position));
    }

    @Override
    public int getItemCount() {
        return exercise.getSeries().size();
    }

    public void deleteItem(int position) {
        String deletedLoad = exercise.getLoad().get(position);
        String deletedRepeat = exercise.getSeries().get(position);
        Integer deletedPosition = position;
        exercise.removeSerie(position);
        notifyDataSetChanged();
    }

//    private void showUndoSnackbar() {
//        Snackbar snackbar = Snackbar.make()
//    }

    public static class SerieViewHolder extends RecyclerView.ViewHolder {
        private EditText repeat;
        private EditText load;
        private TextView serieNumber;
        private int position;

        public SerieViewHolder(@NonNull View itemView, int position) {
            super(itemView);
            this.position = position;
            serieNumber = itemView.findViewById(R.id.SC_seriesNumber);
            load = itemView.findViewById(R.id.SC_load);
            repeat = itemView.findViewById(R.id.SC_repeatNumber);
            load.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    User.getInstance().getEditedTraining().getExercises()
                            .get(position).getLoad().set(getAdapterPosition(),s.toString());
                }
            });

            repeat.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    User.getInstance().getEditedTraining().getExercises()
                            .get(position).getSeries().set(getAdapterPosition(),s.toString());
                }
            });
        }

        public EditText getRepeat() {
            return repeat;
        }

        public EditText getLoad() {
            return load;
        }

        public TextView getSerieNumber() {
            return serieNumber;
        }
    }
}
