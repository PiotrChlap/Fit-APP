package com.example.fitapp.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Exercise {
    private String description;
    private String name;
    private String ytLink;
    private String category;
    private String idExercise;
    private ArrayList<String> series = new ArrayList<>();
    private ArrayList<String> load = new ArrayList<>();


    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getYtLink() {
        return ytLink;
    }

    public String getIdExercise() {
        return idExercise;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<String> getSeries() {
        return series;
    }

    public ArrayList<String> getLoad() {
        return load;
    }

    public void setSeries(ArrayList<String> series) {
        this.series = series;
    }

    public void setLoad(ArrayList<String> load) {
        this.load = load;
    }

    public String getSeriesString(){
        StringBuilder sb = new StringBuilder(series.get(0));
        for(int i =1; i < series.size(); i++){
            sb.append(", ");
            sb.append(series.get(i));
        }
        return sb.toString();

    }

    public void updateDetailedDate(String description, String name, String ytLink, String category) {
        this.description = description;
        this.name = name;
        this.ytLink = ytLink;
        this.category = category;
    }

    public Exercise(String idExercise, ArrayList<String> series) {
        this.idExercise = idExercise;
        for(String tmp : series) {
            String[] tmp2 = tmp.split("x");
            if(tmp2.length == 2){
                this.series.add(tmp2[0]);
                this.load.add(tmp2[1]);
            }
            else {
                this.series.add("Bład");
                this.load.add("Bład");
            }

        }
    }

    //Konstruktor do tworzenia ćwiczeń do wikipedii
    public Exercise(String category, String name, String ytLink, String description, String idExercise){
        this.category = category;
        this.name = name;
        this.ytLink = ytLink;
        this.description = description;
        this.idExercise = idExercise;
    }

    public Exercise createNewExerciseTemplateFromThis(){
        ArrayList<String> series = new ArrayList<>();
        series.add("20x10");
        series.add("15x20");
        series.add("10x30");
        series.add("5x40");
        Exercise ex = new Exercise(idExercise, series);
        ex.updateDetailedDate(description, name, ytLink, category);
        return ex;
    }

    public HistoryExercise getHistoryExercise(){
        HistoryExercise hes = new HistoryExercise(idExercise,new ArrayList<String>(load), new ArrayList<>(series), name);
        return hes;
    }

    public void addSeries(String load, String repeat){
        this.series.add(repeat);
        this.load.add(load);
    }

    public Exercise getDeepCopy(){
        ArrayList<String> load = new ArrayList<>();
        ArrayList<String> serie = new ArrayList<>();
        for(int i =0; i<this.load.size();i++){
            serie.add(this.series.get(i));
            load.add(this.load.get(i));
        }
        Exercise exercise = new Exercise(category, name, ytLink, description, idExercise);
        exercise.setLoad(load);
        exercise.setSeries(serie);
        return exercise;
    }

    public HashMap<String, Object> getMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", idExercise);
        ArrayList<String> serieToSend = new ArrayList<>();
        for (int i = 0; i < load.size(); i++){
            StringBuilder sb = new StringBuilder();
            sb.append(this.series.get(i));
            sb.append("x");
            sb.append(this.load.get(i));
            serieToSend.add(sb.toString());
        }
        map.put("series", serieToSend);
        return map;
    }

    public void removeSerie(int position){
        series.remove(position);
        load.remove(position);
    }
}
