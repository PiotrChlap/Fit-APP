package com.example.fitapp.model;

import java.util.ArrayList;

public class HistoryExercise {
    private String idExercise;
    private String name;
    private ArrayList<String> load = new ArrayList<>();
    private ArrayList<String> series = new ArrayList<>();
    private ArrayList<Boolean> checking = new ArrayList<>();

    public HistoryExercise(String idExercise, ArrayList<String> load, ArrayList<String> series, String name) {
        this.idExercise = idExercise;
        this.load = load;
        this.series = series;
        this.name = name;
        for (int i =0; i< series.size(); i++ ){
            checking.add(false);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdExercise() {
        return idExercise;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getLoad() {
        return load;
    }

    public ArrayList<String> getSeries() {
        return series;
    }

    public void changeLoadValue(int serie, String change){
        load.set(serie,change);
    }

    public void changeRepeatValue(int serie, String change){
        series.set(serie, change);
    }

    public void changeCheckingValue(int seria, Boolean change){
        checking.set(seria, change);
    }

    public ArrayList<Boolean> getChecking() {
        return checking;
    }

    public String getSeriesString(){
        StringBuilder tmp = new StringBuilder();
        int x = checkAllTrainingChecked();
        if(x == 1){
            tmp.append(" Nie wykonano ćwiczenia.");
        } else if(x == 0){
            tmp.append("Serii ");
            int i =0;
            for (String s : series){
                if(!s.equals("Nie wykonano")){
                    i+=1;
                }
            }
            tmp.append(" ");
            tmp.append(i);
            tmp.append(" powtórzeń ");
            for (String s : series){
                if(!s.equals("Nie wykonano")){
                    tmp.append(s);
                    tmp.append(", ");
                }
            }
            tmp.append(" ");
            tmp.append(String.valueOf(series.size()-i));
            tmp.append(" serii nie wykonano");

        }else {
            tmp.append("Serii ");
            tmp.append(load.size());
            tmp.append(" Powtórzeń: ");
            tmp.append(String.join(", ", series));
        }
        return tmp.toString();
    }

    public void checkChecking(){
        ArrayList<String> loadToDisplay = new ArrayList<>();
        ArrayList<String> seriesToDisplay = new ArrayList<>();
        for (int i =0; i<checking.size();i++){
            if(checking.get(i)){
                loadToDisplay.add(load.get(i));
                seriesToDisplay.add(series.get(i));
            } else {
                loadToDisplay.add("Nie wykonano");
                seriesToDisplay.add("Nie wykonano");
            }
        }
        series = seriesToDisplay;
        load = loadToDisplay;
    }

    public int checkAllTrainingChecked(){
        int flaga = 0;
        for(String s : series){
            if(s.equals("Nie wykonano")){
                flaga+=1;
            }
        }
        //Żadna seria nie zostala pomniniea
        if(flaga == 0){
            return -1;
        } else if(flaga < series.size()){
            //jakas seria zostala pominieta
            return 0;
        }
        //wszystkie serie nie wykonane
        return 1;
    }
}
