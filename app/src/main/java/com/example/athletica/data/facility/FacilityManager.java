package com.example.athletica.data.facility;

import android.content.Context;
import android.text.TextUtils;

import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.user.DataManager;
import com.google.firebase.database.DatabaseReference;


/*
This control class is used for running the functions corresponding to the ViewFacility Activity
 */


public class FacilityManager {

    String facilityIndex;
    Facility facility;
    private DataManager dataManager;

    public FacilityManager(Context context, String facilityIndex) {
        this.facilityIndex = facilityIndex;
        dataManager = new DataManager();
        facility = dataManager.readIndex(context, facilityIndex);

    }

    public void addRating(float submitted_rating){
        DatabaseReference Ratings_DB_Ref = facility.getRatings_DB_Ref();
        String userid=facility.getUserid();
        String id = Ratings_DB_Ref.push().getKey();

        Ratings ratings_;
        ratings_ = new Ratings(submitted_rating, id);
        Ratings_DB_Ref.child(facilityIndex).child(userid).setValue(ratings_);
    }

    public boolean addComments(String comment) {
        DatabaseReference Comments_DB_Reference = facility.getComments_DB_Reference();

        String userName= LoginRegisterManager.loggedUser.getProfile().getName();
        String userid = facility.getUserid();
        String comment_content = comment;
        if (!TextUtils.isEmpty(comment_content)) {
            String id = Comments_DB_Reference.push().getKey();
            Comments comments_ = new Comments(userid, userName, comment_content);
            Comments_DB_Reference.child(facilityIndex).child(id).setValue(comments_);
            return true;

        }
        return false;


    }


    public void addRatings() {

    }

}
