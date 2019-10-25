package com.example.athletica.data.user;

import java.util.ArrayList;

public class UserProfile {

    private String name;
    private String gender;
    private String birthdate;
    private String bio;
    private ArrayList<String> sportPreferences;

    public UserProfile(String name, String gender, String birthdate, String bio) {
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ArrayList<String> getSportPreferences() {
        return sportPreferences;
    }

    public void setSportPreferences(ArrayList<String> sportPreferences) {
        this.sportPreferences = sportPreferences;
    }
}