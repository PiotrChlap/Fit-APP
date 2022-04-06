package com.example.fitapp.recycleradapter;

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

public class TreningRecyclerAdapter extends RecyclerView.Adapter<TreningRecyclerAdapter.TraningViewHolder> {
    private User user;
    private OnCardListener mOnCardListener;


    public TreningRecyclerAdapter(OnCardListener onCardListener) {
        user = User.getInstance();
        this.mOnCardListener = onCardListener;

    }

    @Override
    public TraningViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.traning_card, viewGroup, false);

        return new TraningViewHolder(view, mOnCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TraningViewHolder holder, int position) {
        Training training = user.getTrainings().get(position);
        holder.getNameTrening().setText(training.getName());

        switch (training.getDay()){
            case "pon":
                holder.getDayImage().setImageResource(R.drawable.pon);
                break;
            case "wt":
                holder.getDayImage().setImageResource(R.drawable.wt);
                break;
            case "sr":
                holder.getDayImage().setImageResource(R.drawable.sr);
                break;
            case "czw":
                holder.getDayImage().setImageResource(R.drawable.czw);
                break;
            case "pt":
                holder.getDayImage().setImageResource(R.drawable.pt);
                break;
            case "sob":
                holder.getDayImage().setImageResource(R.drawable.sob);
                break;
            default:
                holder.getDayImage().setImageResource(R.drawable.nd);
                break;
        }


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return user.getTrainings().size();
    }

    public interface OnCardListener{
        void onCardClick(int position);
    }

    public static class TraningViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView dayImage;
        private TextView nameTrening;
        private OnCardListener  onCardListener;


        public TraningViewHolder(View view, OnCardListener onCardListener) {
            super(view);
            dayImage = view.findViewById(R.id.TC_dayImage);
            nameTrening = view.findViewById(R.id.TC_nameTraning);
            view.setOnClickListener(this);
            this.onCardListener = onCardListener;
        }

        public ImageView getDayImage() {
            return dayImage;
        }

        public TextView getNameTrening() {
            return nameTrening;
        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }
}
