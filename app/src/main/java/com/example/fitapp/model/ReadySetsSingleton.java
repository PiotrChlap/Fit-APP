package com.example.fitapp.model;

import java.util.ArrayList;

public class ReadySetsSingleton {
    private ArrayList<Training> trainings = new ArrayList<Training>();
    private static ReadySetsSingleton instance;

    public ReadySetsSingleton() {
    }

    public static ReadySetsSingleton getInstance() {
        if (instance == null){
            instance = new ReadySetsSingleton();
        }
        return instance;
    }


    public void setTrainings(ArrayList<Training> trainings) {
//        trainings.clear();
        this.trainings = trainings;
    }

    public ArrayList<Training> getTrainings() {
        return trainings;
    }
}
