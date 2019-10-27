package com.example.athletica.data.facility;

import android.content.Context;
import android.location.Geocoder;
import android.text.TextUtils;
import android.widget.ListView;

import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.user.DataManager;
import com.example.athletica.ui.facility.Comments;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FacilityManager {

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


    public FacilityManager(Context context, String facilityIndex){
        dataManager=new DataManager();
        this.context=context;
        this.facilityIndex=facilityIndex;
        this.facility=dataManager.readIndex(context,facilityIndex) ;
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

    //comments

    public void addRatings(){

    }




    public boolean addComments(String comment){
        String comment_content=comment;
        Comments_DB_Reference=getComments_DB_Reference();
        if(!TextUtils.isEmpty(comment_content)){
            String id= Comments_DB_Reference.push().getKey();

            Comments comments_=new Comments(userid, comment_content);
            Comments_DB_Reference.child(facilityIndex).child(id).setValue(comments_);
            return true;

        }
        return false;




    }





}

