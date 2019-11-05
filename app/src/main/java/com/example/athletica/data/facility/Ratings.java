package com.example.athletica.data.facility;

public class Ratings {
    private String ratingID;
    private String facilityID;

    private float ratingContent;


    public Ratings() {
    }


    public Ratings(float ratingContent, String ratingID) {
        this.ratingContent = ratingContent;
        this.ratingID = ratingID;
    }

    public float getRatingContent() {
        return ratingContent;
    }

    public String getRatingID() {
        return ratingID;
    }
}