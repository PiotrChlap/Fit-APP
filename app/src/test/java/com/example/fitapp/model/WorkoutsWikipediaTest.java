package com.example.fitapp.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class WorkoutsWikipediaTest {

    private WorkoutsWikipedia workoutsWikipedia;

    @Before
    public void setUp() throws Exception {
        workoutsWikipedia = WorkoutsWikipedia.getInstance();
    }

    @Test
    public void getInstance() {
    }

    @Test
    public void addArrayExercises() {
        Exercise exercise = new Exercise("id", new ArrayList<>());
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);
        workoutsWikipedia.addArrayExercises("cat", exercises);
        assertEquals("id", workoutsWikipedia.getExerciseCategory("cat").get(0).getIdExercise());
    }

    @Test
    public void getExerciseCategory() {
        assertNull(workoutsWikipedia.getExerciseCategory("cat2"));
    }
}