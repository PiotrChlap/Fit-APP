package com.example.fitapp.model;

import static org.junit.Assert.*;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TrainingTest {

    private Training training;

    @Before
    public void setUp() throws Exception {
        Exercise exercise = new Exercise("brzuch",
                "brzuszki",
                "URL",
                "opis",
                "idCwiczenia");
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);
        training = new Training("trening", exercises, "wtorek", "idTraining", new ArrayList<>());
    }

    @Test
    public void setIdTraining() {
        assertEquals("idTraining", training.getIdTraining());        assertEquals("idTraining", training.getIdTraining());
        training.setIdTraining("idTraining2");
        assertEquals("idTraining2", training.getIdTraining());
    }

    @Test
    public void getIdTraining() {
        assertEquals("idTraining", training.getIdTraining());
    }

    @Test
    public void setDays() {
        ArrayList<String> days = new ArrayList<>();
        days.add("czwartek");
        days.add("piatek");
        training.setDays(days);
        assertEquals("piatek", training.getDays().get(1));
    }
    
    @Test
    public void setDay() {
        training.setDay("sroda");
        assertEquals("sroda", training.getDay());
    }

    @Test
    public void getDay() {
        assertEquals("wtorek", training.getDay());
    }

    @Test
    public void setName() {
        training.setName("newname");
        assertEquals("newname", training.getName());
    }

    @Test
    public void getName() {
        assertEquals("trening", training.getName());
    }

    @Test
    public void getExercises() {
        Exercise exercise = new Exercise("brzuch",
                "brzuszki",
                "URL",
                "opis",
                "idCwiczenia");
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);
        assertEquals(exercises.get(0).getIdExercise(), training.getExercises().get(0).getIdExercise());
    }

    @Test
    public void addExercise() {
        Exercise exercise = new Exercise("brzuch",
                "brzuszki",
                "URL",
                "opis",
                "idCwiczenia");
        training.addExercise(exercise);
        assertEquals(exercise.getIdExercise(), training.getExercises().get(0).getIdExercise());
    }

    @Test
    public void delExercise() {
        Exercise exercise = new Exercise("brzuch",
                "brzuszki",
                "URL",
                "opis",
                "idCwiczenia");
        training.addExercise(exercise);
        assertEquals(exercise.getIdExercise(), training.getExercises().get(0).getIdExercise());
        training.delExercise(exercise);
        assertEquals(exercise.getIdExercise(), training.getExercises().get(0).getIdExercise());
    }

    @Test
    public void getAllIdExercises() {
        String id = "idCwiczenia";
        ArrayList<String> exercises = new ArrayList<>();
        exercises.add(id);
        assertEquals(exercises, training.getAllIdExercises());
    }

    @Test
    public void getHistoryTraining() {
        HistoryExercise historyExercise =
                new HistoryExercise("idCwiczenia", null, new ArrayList<>(), "nazwa");
        ArrayList<HistoryExercise> historyExercises = new ArrayList<>();
        historyExercises.add(historyExercise);
        HistoryTraining historyTraining = new HistoryTraining("trening", new Date(), historyExercises);
        assertEquals(training.getHistoryTraining().getName(), historyTraining.getName());
        assertEquals(training.getHistoryTraining().getHistoryExercises().get(0).getIdExercise(),
                historyTraining.getHistoryExercises().get(0).getIdExercise());
    }

    @Test
    public void getDeepCopy() {
        Training testTraining = training.getDeepCopy();
        assertEquals(testTraining.getHistoryTraining().getHistoryExercises().get(0).getIdExercise(),
                training.getHistoryTraining().getHistoryExercises().get(0).getIdExercise());
    }

    @Test
    public void getMap() {
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<String> days = new ArrayList<>();
        days.add("wtorek");
        days.add("sroda");
        map.put("name", "trening");
        map.put("days", days);
        training.setDays(days);
        ArrayList<HashMap<String,Object>> exercisesToSend = new ArrayList<>();
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("idCwiczenia", new ArrayList<>()));
        for (Exercise exercise: exercises){
            exercisesToSend.add(exercise.getMap());
        }
        map.put("exercises", exercisesToSend);
        assertEquals(map, training.getMap());
    }
}