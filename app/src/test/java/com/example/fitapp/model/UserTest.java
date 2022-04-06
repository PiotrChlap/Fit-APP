package com.example.fitapp.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserTest {

    private User user;
    private Training training;
    private Date date;

    @Before
    public void setUp() throws Exception {
        user = User.getInstance();
        date = new Date();
        training = new Training("trening", null,"wtorek", "id", new ArrayList<>());
        ArrayList<Achivment> achivments = new ArrayList<>();
        achivments.add(new Achivment(false, 0));
        achivments.add(new Achivment(true, 2));
        user.setAchivments(achivments);
    }

    @After
    public void tearDown() throws Exception {
        user.delUser();
    }

    @Test
    public void getPositionEditedTraining() {
        assertEquals(-1, user.getPositionEditedTraining());
    }

    @Test
    public void setPositionEditedTraining() {
        user.setPositionEditedTraining(1);
        assertEquals(1, user.getPositionEditedTraining());
    }

    @Test
    public void getEditedTraining() {
        assertNull(user.getEditedTraining());
    }

    @Test
    public void setEditedTraining() {
        user.setEditedTraining(training);
        assertEquals("trening", user.getEditedTraining().getName());
    }

    @Test
    public void getTrainings() {
    }

    @Test
    public void setTrainings() {
        ArrayList<Training> trainings = new ArrayList<>();
        trainings.add(training);
        user.setTrainings(trainings);
        assertEquals("trening", user.getTrainings().get(0).getName());
    }

    @Test
    public void addTraning() {
        user.addTraning(training);
        assertEquals("trening", user.getTrainings().get(0).getName());
    }

    @Test
    public void delTraning() {
        user.addTraning(training);
        assertEquals("trening", user.getTrainings().get(0).getName());
        user.delTraning(training);
        assertEquals(new ArrayList<>(), user.getTrainings());
    }

    @Test
    public void setActuallTraining() {
        HistoryTraining historyTraining =
                new HistoryTraining("imie", date, new ArrayList<>());
        user.setActuallTraining(historyTraining);
        assertEquals("imie", user.getActuallTraining().getName());
    }

    @Test
    public void getActuallTraining() {
        assertNull(user.getActuallTraining());
    }

    @Test
    public void getInstance() {
        assertNull(User.getInstance().getUsername());
    }

    @Test
    public void userToMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("Login", "user");
        userMap.put("Email", "user@user.pl");
        userMap.put("Password", null);
        user.init("user", "user@user.pl");
        assertEquals(userMap, user.userToMap());
    }

    @Test
    public void init() {
        user.init("user", "user@user.pl");
        assertEquals("user", user.getUsername());
        assertEquals("user@user.pl", user.getEmail());
    }

    @Test
    public void getUsername() {
    }

    @Test
    public void getEmail() {
    }

    @Test
    public void setHistoryTrainings() {
        ArrayList<HistoryTraining> historyTrainings =
                new ArrayList<>();
        historyTrainings.add(
                new HistoryTraining("htrening", date, new ArrayList<>()));
        user.setHistoryTrainings(historyTrainings);
        assertEquals("htrening", user.getHistoryTrainings().get(0).getName());
        assertEquals(date, user.getHistoryTrainings().get(0).getData());
    }

    @Test
    public void getIndexTraining() {
        ArrayList<Training> trainings = new ArrayList<>();
        trainings.add(training);
        user.setTrainings(trainings);
        assertEquals(0, user.getIndexTraining("id"));
        user.deleteTraining("id");
        assertEquals(-1, user.getIndexTraining("id"));
    }

    @Test
    public void delUser() {
//        INSTANCE.delUser();
    }

    @Test
    public void getAchivments() {
        assertEquals(0, user.getAchivments().get(0).getAmount());
        assertEquals(false, user.getAchivments().get(0).getAdded());
    }

    @Test
    public void getAchivmentsHasmap() {
        ArrayList<HashMap<String, Object>> send = new ArrayList<>();
        ArrayList<Achivment> achivments = new ArrayList<>();
        achivments.add(new Achivment(false, 0));
        achivments.add(new Achivment(true, 2));
        for (Achivment achivment : achivments){
            send.add(achivment.getMap());
        }
        assertEquals(send, user.getAchivmentsHasmap());
    }
}