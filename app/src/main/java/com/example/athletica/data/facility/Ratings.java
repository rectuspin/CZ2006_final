package com.example.athletica.data.facility;

public class Ratings {
    //private String ratingID;
    //private String facilityID;
    private float ratingContent;
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


    public Ratings() {
    }


    public Ratings(int num) {
        five += num;
    }
    public float addrating(float ratingContent){
        switch((int)(10*ratingContent)){
            case 50:
                five++;
                break;
            case 45:
                fourpfive++;
                break;

        }
        return five;
    }
    public float getFive() {
        return five;
    }

    public float getRatingContent() {
        return ratingContent;
    }
}