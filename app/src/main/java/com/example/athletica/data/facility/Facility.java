package com.example.athletica.data.facility;

import android.content.Context;
import android.location.Geocoder;

import com.example.athletica.data.account.LoginRegisterManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Locale;


public class Facility {

    Geocoder geocoder;
    DatabaseReference Comments_DB_Reference;
    DatabaseReference Ratings_DB_Ref;
    private String facilityIndex;
    private String facilities, userid, name, website, address;
    private double lat, lng;


    public Facility(String index, String lng, String lat, String name, String facilities, String zip, String website) {


        this.userid = LoginRegisterManager.loggedUser.getId();
        this.Comments_DB_Reference = FirebaseDatabase.getInstance().getReference("facility_comments");
        this.Ratings_DB_Ref = FirebaseDatabase.getInstance().getReference("facility_ratings");


        this.facilityIndex = index;
        this.facilities = facilities.replace("/", "  ");
        this.name = name;
        this.website = website;
        this.lat = Double.parseDouble(lat);
        this.lng = Double.parseDouble(lng);
        this.address = zip;

    }

    public String getFacilities() {
        return facilities;
    }

    public String getFacilityIndex() {
        return facilityIndex;
    }

    public String getUserid() {
        return userid;
    }

    public DatabaseReference getComments_DB_Reference() {
        return Comments_DB_Reference;
    }

    public DatabaseReference getRatings_DB_Ref() {
        return Ratings_DB_Ref;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public double getLat() {
        return lat;
    }

    public double getLong() {
        return lng;
    }

    public String getAddress(Context context) {
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            return geocoder.getFromLocation(this.lat, this.lng, 1).get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }


}

