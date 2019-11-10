package com.example.athletica.data.facility;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.user.DataManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;


public class FacilityManager {

    String facilityIndex;
    Facility facility;
    private DataManager dataManager;
    float userRating;

    public FacilityManager(Context context, String facilityIndex) {
        this.facilityIndex = facilityIndex;
        dataManager = new DataManager();
        facility = dataManager.readIndex(context, facilityIndex);

    }

    public void addRating(float submitted_rating) {
        DatabaseReference Ratings_DB_Ref = facility.getRatings_DB_Ref();
        String userid = facility.getUserid();
        String id = Ratings_DB_Ref.push().getKey();

        Ratings ratings_;
        ratings_ = new Ratings(submitted_rating, id,userid);
        Ratings_DB_Ref.child(facilityIndex).child(userid).setValue(ratings_);
    }

    public boolean addComments(String comment) {
        DatabaseReference Comments_DB_Reference = facility.getComments_DB_Reference();

        String userName = LoginRegisterManager.loggedUser.getProfile().getName();
        String userid = facility.getUserid();
        String comment_content = comment;

//        DatabaseReference rat = FirebaseDatabase.getInstance().getReference("facility_ratings").child(facilityIndex);
//        rat.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
//                    Ratings rating = Snapshot.getValue(Ratings.class);
//                    if(userid==rating.getUserID())
//                        userRating = rating.getRatingContent();
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
        if (!TextUtils.isEmpty(comment_content)) {
            String id = Comments_DB_Reference.push().getKey();

            Comments comments_ = new Comments(userid, userName, comment_content,userRating);
            Comments_DB_Reference.child(facilityIndex).child(id).setValue(comments_);
            return true;

        }
        return false;


    }

}
