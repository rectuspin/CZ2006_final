package com.example.athletica.data.facility;

public class Ratings {
    private String ratingID;
    private String facilityID;

    private float ratingContent;
    /*
    private float five = 0;
    private float fourpfive = 0;
    private float four = 0;
    private float threepfive = 0;
    private float three = 0;
    private float twopfive = 0;
    private float two = 0;
    private float onepfive = 0;
    private float one = 0;
    private float pfive = 0;
    private float zero = 0;
    */

    public Ratings() {
    }


    public Ratings(float ratingContent,String ratingID) {
        this.ratingContent=ratingContent;
        this.ratingID=ratingID;
    }

    public float getRatingContent() {
        return ratingContent;
    }

    public String getRatingID() {
        return ratingID;
    }
}