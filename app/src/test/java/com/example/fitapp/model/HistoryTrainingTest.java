package com.example.fitapp.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class HistoryTrainingTest {

    private HistoryTraining historyTraining;
    private Date date;

    @Before
    public void setUp() throws Exception {
        date = new Date();
        ArrayList<String> series = new ArrayList<>();
        series.add("brzuszki");
        series.add("plecki");
        HistoryExercise historyExercise =
                new HistoryExercise("id", new ArrayList<>(), series, "nazwa");
        ArrayList<HistoryExercise> historyExercises =
                new ArrayList<>();
        historyExercises.add(historyExercise);
        historyTraining = new HistoryTraining("name", date, historyExercises);
    }

    @Test
    public void getName() {
        assertEquals("name", historyTraining.getName());
    }

    @Test
    public void getData() {
        assertEquals(date, historyTraining.getData());
    }

    @Test
    public void getHistoryExercises() {
        assertEquals("id", historyTraining.getHistoryExercises().get(0).getIdExercise());
    }

    @Test
    public void checkChecking() {
        assertEquals("id", historyTraining.getHistoryExercises().get(0).getIdExercise());
        historyTraining.checkChecking();
        assertEquals(false, historyTraining.getHistoryExercises().get(0).getChecking().get(1));
        historyTraining.getHistoryExercises().get(0).changeCheckingValue(1, true);
        historyTraining.checkChecking();
        assertEquals(true, historyTraining.getHistoryExercises().get(0).getChecking().get(1));
    }

    @Test
    public void checkAllTrainingChecked() {
//        assertEquals("id", historyTraining.getHistoryExercises().get(0).getIdExercise());
//        historyTraining.checkChecking();
//        assertEquals(false, historyTraining.getHistoryExercises().get(0).getChecking().get(1));
//        historyTraining.getHistoryExercises().get(0).changeCheckingValue(1, true);
//        historyTraining.checkChecking();
//        assertEquals(true, historyTraining.getHistoryExercises().get(0).getChecking().get(1));
//
        ArrayList<String> load = new ArrayList<>();
        ArrayList<String> series = new ArrayList<>();
        load.add("Nie wykonano");
        load.add("Nie wykonano");
        series.add("Nie wykonano");
        series.add("Nie wykonano");
        String name = "nazwa2";
        HistoryExercise historyExercise = new HistoryExercise("zwis2", load, series, name);
        ArrayList<HistoryExercise> historyExercises =
                new ArrayList<>();
        historyExercises.add(historyExercise);
        historyTraining = new HistoryTraining("name", date, historyExercises);
        assertFalse(historyTraining.checkAllTrainingChecked());
        load = new ArrayList<>();
        series = new ArrayList<>();
        load.add("20");
        load.add("22");
        load.add("24");
        series.add("16");
        series.add("18");
        series.add("20");
        historyExercise = new HistoryExercise("zwis3", load, series, "nazwa3");
        historyExercises = new ArrayList<>();
        historyExercises.add(historyExercise);
        historyExercises.add(historyExercise);
        historyTraining = new HistoryTraining("name2", date, historyExercises);
        assertTrue(historyTraining.checkAllTrainingChecked());
    }
}