package com.example.fitapp.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class AchivmentTest {

    private Achivment achivment;

    @Before
    public void setUp() throws Exception {
        achivment = new Achivment(false, 0);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAdded() {
    }

    @Test
    public void getAmount() {

    }

    @Test
    public void addAchiv() {
        assertEquals(false, achivment.getAdded());
        achivment.addAchiv();
        assertEquals(true, achivment.getAdded());
    }

    @Test
    public void riseAmount() {
        assertEquals(0, achivment.getAmount());
        achivment.riseAmount();
        achivment.riseAmount();
        assertEquals(2, achivment.getAmount());
    }

    @Test
    public void getMap() {
        achivment.addAchiv();
        achivment.riseAmount();
        HashMap<String, Object> send = new HashMap<>();
        send.put("added", true);
        send.put("amount", "1");
        assertEquals(send, achivment.getMap());
    }
}