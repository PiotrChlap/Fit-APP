package com.example.fitapp.model;

import java.util.HashMap;

public class Achivment {
    private Boolean added;
    private int amount;

    public Achivment(Boolean added, int amount) {
        this.added = added;
        this.amount = amount;
    }

    public Boolean getAdded() {
        return added;
    }

    public int getAmount() {
        return amount;
    }
    public void addAchiv(){
        added = true;
    }

    public void riseAmount(){
        amount+=1;
    }

    public HashMap<String ,Object> getMap(){
        HashMap<String ,Object> send  = new HashMap<>();
        send.put("added", added);
        send.put("amount", String.valueOf(amount));
        return send;
    }
}
