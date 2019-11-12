package com.example.athletica.data.profile;

import android.content.Context;
import android.view.View;

import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.user.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ProfileManager {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private Context context;
    UserProfile currentProfile;
    private LoginRegisterManager loginRegisterManager;

    public ProfileManager(Context context) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }


    public ProfileManager(Context context,UserProfile currentProfile) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        this.currentProfile=currentProfile;
        loginRegisterManager=new LoginRegisterManager();


    }


    public String getCurrentUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return currentUser.getUid();
    }

    public static int calculateAge(String dob) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar birthday = Calendar.getInstance();
        try {
            birthday.setTime(simpleDateFormat.parse(dob));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        Calendar now = Calendar.getInstance();
        int age = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        if (birthday.get(Calendar.MONTH) > now.get(Calendar.MONTH))
            age--;
        else if (birthday.get(Calendar.MONTH) == now.get(Calendar.MONTH))
        {
            if (birthday.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH))
                age--;
        }
        return age;
    }



//
//    public String follow(View view,UserProfile selectedProfile,String text) {
//        ArrayList<String> newFollows = currentProfile.getFollows();
//        String id = selectedProfile.getId();
//        String newFollowers;
//
//        if (text.equals("FOLLOW")) {
//            newFollows.add(selectedProfile.getId());
//            if (selectedProfile.getFollowers() != null) {
//                newFollowers = String.valueOf(Integer.parseInt(selectedProfile.getFollowers()) + 1);
//            } else {
//                newFollowers = "1";
//            }
//
//            loginRegisterManager.follow(newFollows, id, newFollowers);
//            //tvFollowers.setText(String.valueOf(Integer.parseInt(tvFollowers.getText().toString()) + 1));
//            return "UNFOLLOW";
//        } else {
//            for (int i = 0; i < newFollows.size(); i++) {
//                if (newFollows.get(i).equals(id)) {
//                    newFollows.remove(i);
//                    break;
//                }
//            }
//            newFollowers = String.valueOf(Integer.parseInt(selectedProfile.getFollowers()) - 1);
//            loginRegisterManager.follow(newFollows, id, newFollowers);
//            //tvFollowers.setText(String.valueOf(Integer.parseInt(tvFollowers.getText().toString()) - 1));
//            //btnFollowUpdate.setText("FOLLOW");
//            return "FOLLOW";
//        }
//
//    }





}