package com.example.fitapp.recycleradapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.model.WorkoutsWikipedia;

public class AddExerciseRecyclerAdapter extends RecyclerView.Adapter<AddExerciseRecyclerAdapter.AddExerciseViewHolder>{
    private OnCardListener onCardListener;
    private String category;

    public AddExerciseRecyclerAdapter(OnCardListener onCardListener, String category) {
        this.onCardListener = onCardListener;
        this.category = category;
    }

    @NonNull
    @Override
    public AddExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_exercise_card, parent, false);

        return new AddExerciseViewHolder(view,onCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AddExerciseViewHolder holder, int position) {
        WorkoutsWikipedia workoutsWikipedia = WorkoutsWikipedia.getInstance();
        holder.getNameExercise().setText(workoutsWikipedia.getExerciseCategory(category).get(position).getName());

    }

    @Override
    public int getItemCount() {
        return WorkoutsWikipedia.getInstance().getExerciseCategory(category).size();
    }

    public interface OnCardListener{
        void onCardClick(int position);
        void infoExercise(int position);
    }

    public static class AddExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameExercise;
        private ImageView addImage;
        private ImageView info;
        private OnCardListener onCardListener;

        public AddExerciseViewHolder(@NonNull View itemView, OnCardListener onCardListener) {
            super(itemView);
            nameExercise = itemView.findViewById(R.id.AEC_nameExercise);
            addImage = itemView.findViewById(R.id.AEC_addImage);
            info = itemView.findViewById(R.id.AEC_info);
            addImage.setOnClickListener(this);
            this.onCardListener = onCardListener;
            info.setOnClickListener(this);
        }

        public TextView getNameExercise() {
            return nameExercise;
        }

        public ImageView getAddImage() {
            return addImage;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.AEC_addImage:
                    onCardListener.onCardClick(getAdapterPosition());
                    break;
                case R.id.AEC_info:
                    onCardListener.infoExercise(getAdapterPosition());


            }
        }
    }
}
