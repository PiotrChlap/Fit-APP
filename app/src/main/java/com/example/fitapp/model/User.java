package com.example.fitapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String username, email, password;
    private static User INSTANCE;
    private ArrayList<Training> trainings = new ArrayList<>();
    private HistoryTraining actuallTraining = null;
    private Training editedTraining = null;
    private int positionEditedTraining = -1;
    private ArrayList<HistoryTraining> historyTrainings = new ArrayList<>();
    private ArrayList<Achivment> achivments = new ArrayList<>();

    public ArrayList<Achivment> getAchivments() {
        return achivments;
    }

    public void setAchivments(ArrayList<Achivment> achivments) {
        this.achivments = achivments;
    }

    public ArrayList<HistoryTraining> getHistoryTrainings() {
        return historyTrainings;
    }

    public void setHistoryTrainings(ArrayList<HistoryTraining> historyTrainings) {
        this.historyTrainings = historyTrainings;
    }

    public int getPositionEditedTraining() {
        return positionEditedTraining;
    }

    public void setPositionEditedTraining(int positionEditedTraining) {
        this.positionEditedTraining = positionEditedTraining;
    }

    public Training getEditedTraining() {
        return editedTraining;
    }

    public void setEditedTraining(Training editedTraining) {
        this.editedTraining = editedTraining;
    }

    public ArrayList<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(ArrayList<Training> trainings) {
        this.trainings = trainings;
    }

    public void addTraning(Training training) {
        trainings.add(training);
    }

    public void delTraning(Training training) {
        trainings.remove(training);
    }

    public User(){

    }

    public void setActuallTraining(HistoryTraining actuallTraining) {
        this.actuallTraining = actuallTraining;
    }

    public HistoryTraining getActuallTraining() {
        return actuallTraining;
    }

    public static User getInstance() {
        User result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized(User.class) {
            if (INSTANCE == null) {
                INSTANCE = new User();
            }
            return INSTANCE;
        }
    }

    public void delUser(){
        INSTANCE = null;
    }

   public Map<String, Object> userToMap(){
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("Login", this.username);
        userMap.put("Email", this.email);
        userMap.put("Password", this.password);
        return userMap;
    }
    public void init(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getIndexTraining(String idTraining){
        for(int i =0; i< trainings.size(); i++){
            if(trainings.get(i).getIdTraining().equals(idTraining)){
                return i;
            }
        }
        return -1;
    }

    public void deleteTraining(String idTraining){
        ArrayList<Training> trainingsToDel = new ArrayList<>();
        for(Training training : trainings){
            if(training.getIdTraining().equals(idTraining)){
                trainingsToDel.add(training);
            }
        }
        for(Training training: trainingsToDel){
            trainings.remove(training);
        }
    }

    public ArrayList<HashMap<String, Object>> getAchivmentsHasmap(){
        ArrayList<HashMap<String, Object>> send  = new ArrayList<>();
        for (Achivment achivment : achivments){
            send.add(achivment.getMap());
        }
        return send;
    }

}
