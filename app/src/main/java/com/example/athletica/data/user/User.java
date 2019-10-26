package com.example.athletica.data.user;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class User {

    private String id, email;
    private UserProfile profile;
    private ArrayList<String> followedUsers, followers;
    private ArrayList<String> eventsJoined;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public User(){

    }

    public User(String id, UserProfile profile, ArrayList<String> followedUsers, ArrayList<String> followers) {
        this.id = id;
        this.profile = profile;
        this.followedUsers = followedUsers;
        this.followers = followers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(ArrayList<String> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public void setEventsJoined(ArrayList<String> eventsJoined) {
        this.eventsJoined = eventsJoined;
    }

    public boolean canJoin(String eventKey) {
        return !eventsJoined.contains(eventKey);
    }

    public void addEventToList(String eventKey) {
        eventsJoined.add(eventKey);
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users_events").child(id);
        databaseReference.setValue(eventsJoined);
    }

}