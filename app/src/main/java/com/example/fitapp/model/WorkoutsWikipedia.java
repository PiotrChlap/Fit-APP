package com.example.fitapp.model;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkoutsWikipedia {
    private static WorkoutsWikipedia instance;
    private HashMap<String, ArrayList<Exercise>> wikipedia = new HashMap<>();

    private WorkoutsWikipedia(){

    }

    public static WorkoutsWikipedia getInstance(){
        if(instance == null){
            instance = new WorkoutsWikipedia();
        }
        return instance;
    }

    public void addArrayExercises(String category, ArrayList<Exercise> exercises){
        if(!this.wikipedia.containsKey(category)) {
            wikipedia.put(category, exercises);
        }
    }

    public ArrayList<Exercise> getExerciseCategory(String category){
        if (this.wikipedia.containsKey(category)) {
            return wikipedia.get(category);
        }
        return null;
    }

}
