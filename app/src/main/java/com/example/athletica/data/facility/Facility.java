package com.example.athletica.data.facility;

import android.content.Context;
import android.location.Geocoder;
import android.widget.ListView;

import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.user.DataManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Facility {

    private String facilityIndex;
    private DataManager dataManager;
    private Map<String,String> facility;
    Geocoder geocoder;

    Context context;


    private String facilities,userid,name,website,address;
    private double lat,lng;



    ListView listViewComments;
    List<Comments> commentsList;

    DatabaseReference Comments_DB_Reference;
    DatabaseReference Ratings_DB_Ref;


    public Facility(Context context, String facilityIndex){
        dataManager=new DataManager();
        this.context=context;
        this.facilityIndex=facilityIndex;
        this.facility=dataManager.readIndex(context,facilityIndex);
        geocoder=new Geocoder(context,Locale.getDefault());
        this.userid= LoginRegisterManager.loggedUser.getId();
        this.Comments_DB_Reference= FirebaseDatabase.getInstance().getReference("facility_comments");
        this.Ratings_DB_Ref=FirebaseDatabase.getInstance().getReference("facility_ratings");


        this.facilities=facility.get("Facilities").replace("/", "  ");
        this.name=facility.get("name");
        this.website=facility.get("website");
        this.lat=Double.parseDouble(facility.get("lat"));
        this.lng=Double.parseDouble(facility.get("long"));
        try {
            this.address=geocoder.getFromLocation(lat, lng, 1).get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
            this.address="";
        }


    }

    public String getFacilities() {
        return facilities;
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

    public double getLat(){
        return lat;
    }

    public double getLong(){
        return lng;
    }

    public String getAddress(){
        return address;
    }










}

