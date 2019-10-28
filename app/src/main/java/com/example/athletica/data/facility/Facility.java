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

    String userid;
    Context context;

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

    }

    public String getFacilities() {
        return facility.get("Facilities").replace("/", "  ");
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
        return facility.get("name");
    }

    public String getWebsite() {
        return facility.get("website");
    }

    public double getLat(){
        return Double.parseDouble(facility.get("lat"));
    }

    public double getLong(){
        return Double.parseDouble(facility.get("long"));
    }

    public String getAddress(){
        double lat=getLat();
        double lng=getLong();

        try {
            return geocoder.getFromLocation(lat, lng, 1).get(0).getAddressLine(0);
        } catch (IOException e) {
            return "Address Could not be fetched";
        }
    }










}

