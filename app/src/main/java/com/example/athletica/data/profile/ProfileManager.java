package com.example.athletica.data.profile;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class ProfileManager {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private Context context;
    private String displayDateFormat = "dd/MM/yyyy HH:mm";
    private SimpleDateFormat sdf = new SimpleDateFormat(displayDateFormat, Locale.getDefault());

    public ProfileManager(Context context) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }


}