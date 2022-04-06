package com.example.fitapp.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class HistoryExerciseTest {

    private HistoryExercise historyExercise;

    @Before
    public void setUp() throws Exception {
        ArrayList<String> load = new ArrayList<>();
        ArrayList<String> series = new ArrayList<>();
        load.add("10");
        load.add("12");
        series.add("14");
        series.add("16");
        String name = "nazwa";
        historyExercise = new HistoryExercise("zwis", load, series, name);
        historyExercise.setName("zwis");
    }

    @Test
    public void setName() {
        historyExercise.setName("nowanazwa");
        assertEquals(historyExercise.getName(), "nowanazwa");
    }

    @Test
    public void getIdExercise() {
    }

    @Test
    public void getName() {
        assertEquals(historyExercise.getName(), "zwis");
    }

    @Test
    public void getLoad() {
    }

    @Test
    public void getSeries() {
    }

    @Test
    public void changeLoadValue() {
        historyExercise.changeLoadValue(0, "18");
        ArrayList<String> load = new ArrayList<>();
        load.add("18");
        load.add("12");
        assertEquals(historyExercise.getLoad(), load);
    }

    @Test
    public void changeRepeatValue() {
        historyExercise.changeRepeatValue(0, "20");
        ArrayList<String> series = new ArrayList<>();
        series.add("20");
        series.add("16");
        assertEquals(historyExercise.getSeries(), series);
    }

    @Test
    public void getSeriesString() {
        assertEquals(historyExercise.getSeriesString(),
                "Serii 2 Powtórzeń: 14, 16");
        ArrayList<String> load = new ArrayList<>();
        ArrayList<String> series = new ArrayList<>();
        load.add("Nie wykonano");
        load.add("Nie wykonano");
        series.add("Nie wykonano");
        series.add("Nie wykonano");
        String name = "nazwa2";
        historyExercise = new HistoryExercise("zwis2", load, series, name);
        historyExercise.setName("zwis2");
        assertEquals(historyExercise.getSeriesString(), " Nie wykonano ćwiczenia.");
        load.add("14");
        series.add("18");
        name = "nazwa3";
        historyExercise = new HistoryExercise("zwis3", load, series, name);
        historyExercise.setName("zwis3");
        assertEquals(historyExercise.getSeriesString(),
                "Serii  1 powtórzeń 18,  2 serii nie wykonano");
    }
}