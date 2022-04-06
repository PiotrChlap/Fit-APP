package com.example.fitapp.recycleradapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.R;
import com.example.fitapp.model.HistoryTraining;
import com.example.fitapp.model.User;

import java.text.Format;
import java.text.SimpleDateFormat;

public class ProgressCardRecyclerAdapter extends RecyclerView.Adapter<ProgressCardRecyclerAdapter.ProgressCardViewHolder>{
    private User user;
    private OnCardListener mOnCardListener;

    public ProgressCardRecyclerAdapter(OnCardListener mOnCardListener) {
        this.mOnCardListener = mOnCardListener;
        this.user = User.getInstance();
    }

    @NonNull
    @Override
    public ProgressCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_card, parent, false);
        return new ProgressCardViewHolder(view, mOnCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressCardViewHolder holder, int position) {
        HistoryTraining historyTraining = user.getHistoryTrainings().get(position);
        holder.getNameTrening().setText(historyTraining.getName());
        Format f = new SimpleDateFormat("EEEE");
        String strResult = f.format(historyTraining.getData());
        switch (strResult){
            case "Monday":
                holder.getDayImage().setImageResource(R.drawable.pon);
                break;
            case "Tuesday":
                holder.getDayImage().setImageResource(R.drawable.wt);
                break;
            case "Wednesday":
                holder.getDayImage().setImageResource(R.drawable.sr);
                break;
            case "Thursday":
                holder.getDayImage().setImageResource(R.drawable.czw);
                break;
            case "Friday":
                holder.getDayImage().setImageResource(R.drawable.pt);
                break;
            case "Saturday":
                holder.getDayImage().setImageResource(R.drawable.sob);
                break;
            default:
                holder.getDayImage().setImageResource(R.drawable.nd);
                break;
        }
        f = new SimpleDateFormat("dd.MM.yyyy");
        holder.getDate().setText(f.format(historyTraining.getData()));
    }

    @Override
    public int getItemCount() {
        return user.getHistoryTrainings().size();
    }

    public interface OnCardListener{
        void onCardClick(int position);
    }

    public static class ProgressCardViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private ImageView dayImage;
        private TextView nameTrening;
        private OnCardListener onCardListener;
        private TextView date;

        public ProgressCardViewHolder(@NonNull View itemView, OnCardListener onCardListener ) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.onCardListener = onCardListener;
            dayImage = itemView.findViewById(R.id.HC_dayImage);
            nameTrening = itemView.findViewById(R.id.HC_nameTraning);
            date = itemView.findViewById(R.id.HC_dateTraning);

        }

        public ImageView getDayImage() {
            return dayImage;
        }

        public TextView getNameTrening() {
            return nameTrening;
        }

        public TextView getDate() {
            return date;
        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }
}
