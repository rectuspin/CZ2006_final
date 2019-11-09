package com.example.athletica.data.facility;

public class Ratings {
    private String ratingID;
    private String facilityID;
    private String userID;
    private float ratingContent;


    public Ratings() {
    }


    public Ratings(float ratingContent, String ratingID,String userID) {
        this.ratingContent = ratingContent;
        this.ratingID = ratingID;
        this.userID=userID;
    }

    public String getUserID() {
        return userID;
    }

    public float getRatingContent() {
        return ratingContent;
    }

    public String getRatingID() {
        return ratingID;
    }
}