package com.example.fitapp.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ExerciseTest {

    private Exercise exercise1;
    private Exercise exercise2;

    @Before
    public void setUp() throws Exception {
        ArrayList<String> series = new ArrayList<>();
        series.add("20x10");
        series.add("150x20");
        exercise1 = new Exercise("zwis", series);
        exercise2 = new Exercise("brzuch",
                "brzuszki",
                "URL",
                "opis",
                "idCwiczenia");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getDescription() {
        assertEquals(exercise2.getDescription(), "opis");
    }

    @Test
    public void getName() {
        assertEquals(exercise2.getName(), "brzuszki");
    }

    @Test
    public void getYtLink() {
        assertEquals(exercise2.getYtLink(), "URL");
    }

    @Test
    public void getIdExercise() {
        assertEquals(exercise1.getIdExercise(), "zwis");
        assertEquals(exercise2.getIdExercise(), "idCwiczenia");
    }

    @Test
    public void getCategory() {
        assertEquals(exercise2.getCategory(), "brzuch");
    }

    @Test
    public void getSeries() {
        ArrayList<String> series = new ArrayList<>();
        series.add("asdfghj");
        series.add("sdfghjkl");
        Exercise newExercise1 = new Exercise("zwis", series);
        exercise1 = new Exercise("zwis2", series);
        assertEquals(exercise1.getSeries(), newExercise1.getSeries());
    }

    @Test
    public void getLoad() {
        ArrayList<String> load = new ArrayList<>();
        load.add("Bład");
        exercise2.setLoad(load);
        assertEquals(exercise2.getLoad(), load);
    }

    @Test
    public void setSeries() {
        ArrayList<String> series = new ArrayList<>();
        series.add("20x10");
        series.add("15x20");
        exercise1.setSeries(series);
        assertEquals(exercise1.getSeries(), series);
    }

    @Test
    public void setLoad() {
        ArrayList<String> load = new ArrayList<>();
        load.add("10");
        load.add("20");
        exercise1.setSeries(load);
        assertEquals(exercise1.getLoad(), load);
    }

    @Test
    public void getSeriesString() {
        ArrayList<String> load = new ArrayList<>();
        load.add("20x10");
        load.add("15x20");
        exercise1.setSeries(load);
        assertEquals(exercise1.getSeriesString(), "20x10, 15x20");
    }

    @Test
    public void updateDetailedDate() {
        exercise2.updateDetailedDate("opis",
                "brzuszki",
                "URL",
                "brzuch");
        assertEquals(exercise2.getDescription(), "opis");
        assertEquals(exercise2.getName(), "brzuszki");
        assertEquals(exercise2.getYtLink(), "URL");
        assertEquals(exercise2.getCategory(), "brzuch");
    }

    @Test
    public void createNewExerciseTemplateFromThis() {
        Exercise newExercise = new Exercise("brzuch",
                "brzuszki",
                "URL",
                "opis",
                "idCwiczenia");
        exercise2.createNewExerciseTemplateFromThis();
        assertEquals(newExercise.getSeries(), exercise2.getSeries());
    }

    @Test
    public void getHistoryExercise() {
        Exercise newExercise2 = new Exercise("brzuch",
                "brzuszki",
                "URL",
                "opis",
                "idCwiczenia");
        assertEquals(exercise2.getHistoryExercise().getIdExercise(),
                    newExercise2.getHistoryExercise().getIdExercise());
    }

    @Test
    public void addSeries() {
        exercise2.addSeries("a","b");
        assertEquals(exercise2.getSeriesString(), "b");
    }

    @Test
    public void getDeepCopy() {
        ArrayList<String> series = new ArrayList<>();
        series.add("20x10");
        series.add("15x20");
        series.add("15x15");
        Exercise newExercise1 = new Exercise("zwis", series);
        exercise1 = newExercise1.getDeepCopy();
        assertEquals(newExercise1.getCategory(), exercise1.getCategory());
        assertEquals(newExercise1.getName(), exercise1.getName());
        assertEquals(newExercise1.getYtLink(), exercise1.getYtLink());
        assertEquals(newExercise1.getDescription(), exercise1.getDescription());
        assertEquals(newExercise1.getIdExercise(), exercise1.getIdExercise());
    }

    @Test
    public void getMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zwis");
        ArrayList<String> serieToSend = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            StringBuilder sb = new StringBuilder();
            sb.append(exercise1.getSeries().get(i));
            sb.append("x");
            sb.append(exercise1.getLoad().get(i));
            serieToSend.add(sb.toString());
        }
        map.put("series", serieToSend);
        assertEquals(exercise1.getMap(), map);
    }

    @Test
    public void removeSerie() {
        exercise1.removeSerie(0);
        assertEquals(exercise1.getSeriesString(),"150");
    }
}