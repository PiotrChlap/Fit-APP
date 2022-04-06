package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitapp.model.User;
import com.example.fitapp.recycleradapter.AddedExercisesRecyclerAdapter;

import java.util.Collections;

public class SwipeToDeleteExerciseCallback extends ItemTouchHelper.SimpleCallback {
    private AddedExercisesRecyclerAdapter adapter;

    public SwipeToDeleteExerciseCallback(AddedExercisesRecyclerAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        Collections.swap(User.getInstance().getEditedTraining().getExercises(), fromPosition, toPosition);
        recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        adapter.deleteItem(position);
    }
}
