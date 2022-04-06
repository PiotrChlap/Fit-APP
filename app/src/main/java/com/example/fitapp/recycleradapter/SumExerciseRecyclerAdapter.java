package com.example.fitapp.recycleradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.model.HistoryTraining;
import com.example.fitapp.model.User;

public class SumExerciseRecyclerAdapter extends RecyclerView.Adapter<SumExerciseRecyclerAdapter.SumExerciseViewHolder> {
    @NonNull
    @Override
    public SumExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sum_exercise_card, viewGroup, false);
        return new SumExerciseRecyclerAdapter.SumExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SumExerciseViewHolder holder, int position) {
        HistoryTraining ht = User.getInstance().getActuallTraining();
        holder.getNameTraining().setText(ht.getHistoryExercises().get(position).getName());
        holder.getSummaryExercise().setText(ht.getHistoryExercises().get(position).getSeriesString());
        if(ht.getHistoryExercises().get(position).checkAllTrainingChecked()==1){
            holder.getCardView().setCardBackgroundColor(holder.getItemView().getContext().getColor(R.color.sum_red));
        } else if(ht.getHistoryExercises().get(position).checkAllTrainingChecked()==0){
            holder.getCardView().setCardBackgroundColor(holder.getItemView().getContext().getColor(R.color.sum_orange));
        }

    }

    @Override
    public int getItemCount() {
        return User.getInstance().getActuallTraining().getHistoryExercises().size();
    }


    public static class SumExerciseViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTraining;
        private TextView summaryExercise;
        private CardView cardView;
        private View itemView;

        public SumExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            cardView = itemView.findViewById(R.id.SEC_card);
            cardView.setCardBackgroundColor(itemView.getContext().getColor(R.color.sum_green));
            nameTraining = itemView.findViewById(R.id.SEC_nameTraining);
            summaryExercise  = itemView.findViewById(R.id.SEC_summaryExercise);
        }

        public TextView getNameTraining() {
            return nameTraining;
        }

        public TextView getSummaryExercise() {
            return summaryExercise;
        }

        public CardView getCardView() {
            return cardView;
        }

        public View getItemView() {
            return itemView;
        }
    }
}
