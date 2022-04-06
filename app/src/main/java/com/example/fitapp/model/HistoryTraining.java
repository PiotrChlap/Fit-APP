package com.example.fitapp.model;

import java.util.ArrayList;
import java.util.Date;

public class HistoryTraining {
    private String name;
    private Date data;
    private ArrayList<HistoryExercise> historyExercises = new ArrayList<>();

    public HistoryTraining(String name, Date data, ArrayList<HistoryExercise> historyExercises) {
        this.name = name;
        this.data = data;
        this.historyExercises = historyExercises;
    }

    public String getName() {
        return name;
    }

    public Date getData() {
        return data;
    }

    public ArrayList<HistoryExercise> getHistoryExercises() {
        return historyExercises;
    }

    public void checkChecking(){
        for (HistoryExercise historyExercise : historyExercises){
            historyExercise.checkChecking();
        }
    }
    public boolean checkAllTrainingChecked(){
        for (HistoryExercise historyExercise : historyExercises){
            if(historyExercise.checkAllTrainingChecked()!=-1){
                return false;
            }
        }
        return true;
    }

}
