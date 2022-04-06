package com.example.fitapp.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ReadySetsSingletonTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setTrainings() {
        ArrayList<Training> trainings = new ArrayList<>();
        trainings.add(new Training("test", new ArrayList<>(), "wtorek", "id", new ArrayList<>()));
        ReadySetsSingleton.getInstance().setTrainings(trainings);
        assertEquals("wtorek", ReadySetsSingleton.getInstance().getTrainings().get(0).getDay());
    }

    @Test
    public void getTrainings() {
    }
}