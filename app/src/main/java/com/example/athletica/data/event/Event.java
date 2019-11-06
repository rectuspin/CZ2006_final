package com.example.athletica.data.event;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Event {

    private String id, name, location, startDate, endDate, description, discipline, createdBy;
    private int maxParticipants;
    private double price;
    private ArrayList<String> participants;
    private int currentParticipants;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public Event() {

    }

    public Event(String name, String location, String startDate, String endDate, String description, String discipline, String createdBy, int maxParticipants, double price, int currentParticipants) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.discipline = discipline;
        this.createdBy = createdBy;
        this.maxParticipants = maxParticipants;
        this.price = price;
        this.currentParticipants = currentParticipants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public double getPrice() {
        return price;
    }

    public boolean canBeJoined() {
        return currentParticipants < maxParticipants || maxParticipants == 0;
    }

    public void addNewParticipant() {
        currentParticipants++;
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("events_info")
                .child(id)
                .child("currentParticipants");
        databaseReference.setValue(currentParticipants);
    }

    public void removeParticipant(){
        currentParticipants--;
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("events_info")
                .child(id)
                .child("currentParticipants");
        databaseReference.setValue(currentParticipants);
    }
}
