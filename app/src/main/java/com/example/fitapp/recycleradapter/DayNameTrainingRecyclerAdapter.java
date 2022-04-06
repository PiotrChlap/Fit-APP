package com.example.fitapp.recycleradapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.model.Training;
import com.example.fitapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DayNameTrainingRecyclerAdapter extends RecyclerView.Adapter<DayNameTrainingRecyclerAdapter.DayNameTrainingViewHolder> {
    private OnCardListener mOnCardListiner;
    private ArrayList<Training> trainings = new ArrayList<>();

    public DayNameTrainingRecyclerAdapter(OnCardListener onCardListener) {
        this.mOnCardListiner = onCardListener;
        ArrayList<Training> trainingsUser = User.getInstance().getTrainings();
        boolean flaga = true;
        for(int i =0; i<trainingsUser.size();i++){
            flaga=true;
            for(int j=0; j<trainings.size();j++){
                if(trainingsUser.get(i).getIdTraining().equals(trainings.get(j).getIdTraining())){
                    flaga=false;
                    break;
                }
            }
            if (flaga){
                trainings.add(trainingsUser.get(i));
            }
        }
    }

    @NonNull
    @Override
    public DayNameTrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_only_name_card_del, parent, false);

        return new DayNameTrainingViewHolder(view, mOnCardListiner);
    }

    @Override
    public void onBindViewHolder(@NonNull DayNameTrainingViewHolder holder, int position) {
        holder.getTrainingName().setText(trainings.get(position).getName());
        holder.setPosition(User.getInstance().getIndexTraining(trainings.get(position).getIdTraining()));
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public void deleteItem(int position) {
        User.getInstance().deleteTraining(trainings.get(position).getIdTraining());
        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Workouts").document(trainings.get(position).getIdTraining())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error deleting document", e);
                    }
                });
        trainings.remove(position);
        notifyDataSetChanged();
    }


    public static class DayNameTrainingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView trainingName;
        private OnCardListener onCardListener;
        private int position;
        private ImageView del;
        private ImageView edit;



        public DayNameTrainingViewHolder(@NonNull View itemView, OnCardListener onCardListener) {
            super(itemView);
            trainingName = itemView.findViewById(R.id.TONCD_nameTraning);
            itemView.setOnClickListener(this);
            this.onCardListener = onCardListener;
            del = itemView.findViewById(R.id.TONCD_del);
            del.setOnClickListener(this);
            edit = itemView.findViewById(R.id.TONCD_edit);
            edit.setOnClickListener(this);

        }

        public TextView getTrainingName() {
            return trainingName;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.TONCD_del:
                    onCardListener.del(getAdapterPosition());
                    break;
                case R.id.TONCD_edit:
                    onCardListener.onCardClick(position);
                    break;

            }

        }
    }

    public interface OnCardListener{
        void onCardClick(int position);
        void del(int position);
    }
}
