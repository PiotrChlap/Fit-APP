package com.example.fitapp.model;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Training {
    private String name;
    private ArrayList<Exercise> exercises;
    private String day;
    private String idTraining;
    private ArrayList<String> days = new ArrayList<>();

    public Training(String name, ArrayList<Exercise> exercises, String day, String idTraining, ArrayList<String> days) {
        this.name = name;
        this.exercises = exercises;
        this.day = day;
        this.idTraining = idTraining;
        this.days = days;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public String getIdTraining() {
        return idTraining;
    }

    public String getDay() {
        return day;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void delExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    public ArrayList<String> getAllIdExercises(){
        ArrayList<String> listId = new ArrayList<>();
        for (Exercise ex : exercises){
            listId.add(ex.getIdExercise());
        }
        return listId;
    }

    public HistoryTraining getHistoryTraining(){
        ArrayList<HistoryExercise> historyExercises = new ArrayList<>();
        for (Exercise ex : exercises){
            historyExercises.add(ex.getHistoryExercise());
        }
        return new HistoryTraining(name,new Date(),historyExercises);
    }

    public Training getDeepCopy(){
        ArrayList<Exercise> exercisesCopy = new ArrayList<>();
        for (Exercise ex : exercises){
            exercisesCopy.add(ex.getDeepCopy());
        }
        return new  Training(name, exercisesCopy, day, idTraining, new ArrayList<>(days));
    }

    public HashMap<String, Object> getMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        ArrayList<HashMap<String,Object>> exercisesToSend = new ArrayList<>();
        for (Exercise exercise: exercises){
            exercisesToSend.add(exercise.getMap());
        }
        map.put("exercises", exercisesToSend);
        map.put("days", days);
        return map;
    }

    public void setIdTraining(String idTraining) {
        this.idTraining = idTraining;
    }

    public void setDay(String day) {
        this.day = day;
    }


}
