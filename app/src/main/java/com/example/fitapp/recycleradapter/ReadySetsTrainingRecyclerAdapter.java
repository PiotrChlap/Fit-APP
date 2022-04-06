package com.example.fitapp.recycleradapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.model.Exercise;
import com.example.fitapp.model.ReadySetsSingleton;
import com.example.fitapp.model.Training;
import com.example.fitapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ReadySetsTrainingRecyclerAdapter extends RecyclerView.Adapter<ReadySetsTrainingRecyclerAdapter.ReadySetsTrainingHolder> {
    private  OnCardListenerMy cardListenerMy;
    private ArrayList<Training> trainings = new ArrayList<>();

    public ReadySetsTrainingRecyclerAdapter(OnCardListenerMy onCardListenerMy) {
        this.cardListenerMy = onCardListenerMy;
        ReadySetsSingleton readySetsSingleton = ReadySetsSingleton.getInstance();
        trainings = readySetsSingleton.getTrainings();
        int x =1;

    }

    @NonNull
    @Override
    public ReadySetsTrainingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_only_name_card, parent, false);


        return new ReadySetsTrainingRecyclerAdapter.ReadySetsTrainingHolder(view, cardListenerMy);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadySetsTrainingHolder holder, int position) {
        holder.getTrainingName().setText(trainings.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public interface  OnCardListenerMy{
        void onCardClick(int position);
    }

    public static class ReadySetsTrainingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView trainingName;
        private ReadySetsTrainingRecyclerAdapter.OnCardListenerMy onCardListener;



        public ReadySetsTrainingHolder(@NonNull View itemView, ReadySetsTrainingRecyclerAdapter.OnCardListenerMy onCardListener) {
            super(itemView);
            trainingName = itemView.findViewById(R.id.TONC_nameTraning);
            itemView.setOnClickListener(this);
            this.onCardListener = onCardListener;

        }

        public TextView getTrainingName() {
            return trainingName;
        }



        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }











}
