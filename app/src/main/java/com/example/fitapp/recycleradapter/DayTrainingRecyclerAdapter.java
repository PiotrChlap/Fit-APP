//package com.example.fitapp.recycleradapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.fitapp.R;
//import com.example.fitapp.model.User;
//
//import java.util.ArrayList;
//import java.util.stream.Collectors;
//
//public class DayTrainingRecyclerAdapter extends RecyclerView.Adapter<DayTrainingRecyclerAdapter.DayTrainingViewHolder> {
//    private  ArrayList<String> days = new ArrayList<>();
//    private  ArrayList<String> dayShortCuts = new ArrayList<>();
//    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
//    private DayNameTrainingRecyclerAdapter.OnCardListener onCardListener;
//
//    public DayTrainingRecyclerAdapter(ArrayList<String> days, ArrayList<String> dayShortCuts, DayNameTrainingRecyclerAdapter.OnCardListener onCardListener) {
//        this.days = days;
//        this.dayShortCuts = dayShortCuts;
//        this.onCardListener = onCardListener;
//
//    }
//
//    @NonNull
//    @Override
//    public DayTrainingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.day_training_card,viewGroup,false);
//
//        return new DayTrainingViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DayTrainingViewHolder holder, int position) {
//        holder.getDayName().setText(days.get(position));
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.getmRecyclerView().getContext(), LinearLayoutManager.VERTICAL,false);
//        linearLayoutManager.setInitialPrefetchItemCount(User.getInstance().getTrainings().stream().filter(training -> training.getDay().contains(dayShortCuts.get(position))).collect(Collectors.toList()).size());
//        DayNameTrainingRecyclerAdapter dayNameTrainingRecyclerAdapter = new DayNameTrainingRecyclerAdapter(dayShortCuts.get(position),onCardListener);
//        holder.getmRecyclerView().setLayoutManager(linearLayoutManager);
//        holder.getmRecyclerView().setAdapter(dayNameTrainingRecyclerAdapter);
//        holder.getmRecyclerView().setRecycledViewPool(viewPool);
//    }
//
//    @Override
//    public int getItemCount() {
//        return days.size();
//    }
//
//
//    public class DayTrainingViewHolder extends RecyclerView.ViewHolder{
//        private TextView dayName;
//        private RecyclerView mRecyclerView;
//
//        public DayTrainingViewHolder(@NonNull View itemView) {
//            super(itemView);
//            dayName = itemView.findViewById(R.id.DTC_nameDay);
//            mRecyclerView = itemView.findViewById(R.id.DTC_trainingRecyclerAdapter);
//        }
//
//        public TextView getDayName() {
//            return dayName;
//        }
//
//        public RecyclerView getmRecyclerView() {
//            return mRecyclerView;
//        }
//    }
//}
