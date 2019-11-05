package com.example.athletica.data.user;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.athletica.R;
import com.example.athletica.data.event.Event;
import com.example.athletica.data.facility.Facility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private FirebaseDatabase firebaseDatabase;
    private Object object;
    private Facility facility;

    public DataManager() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getUser(final DataStatus dataStatus, final String id) {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users_info").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                User user = new User(id, userProfile, null, null);
                dataStatus.dataLoaded(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getProfileByKey(final DataStatus dataStatus, final String profileId) {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users_info").child(profileId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile profile = dataSnapshot.getValue(UserProfile.class);
                dataStatus.dataLoaded(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getUserEvents(final DataStatus dataStatus, final String id) {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users_events").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> eventKeys = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    eventKeys.add((String) data.getValue());
                }
                dataStatus.dataLoaded(eventKeys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getEventKeys(final DataStatus dataStatus, final long amount, final String str) {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("events_info");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Map> eventIds = new ArrayList<>();
                long amountToDownload = amount;
                int counter = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Map<String, String> eventDetails = new HashMap<>();

                    if (data.child("name").getValue(String.class).toLowerCase().contains(str.toLowerCase()) || data.child("description").getValue(String.class).toLowerCase().contains(str.toLowerCase())) {
                        eventDetails.put("key", data.getKey());
                        eventDetails.put("name", data.child("name").getValue(String.class));
                        eventDetails.put("startDate", data.child("startDate").getValue(String.class));
                        eventIds.add(eventDetails);
                        counter++;
                        if (counter >= amountToDownload)
                            break;
                    }
                }
                dataStatus.dataLoaded(eventIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getEventByKey(final DataStatus dataStatus, final String eventId) {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("events_info").child(eventId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                dataStatus.dataLoaded(event);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAllUsers(final DataStatus dataStatus, final String query) {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users_info");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Map> userIds = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Map<String, String> userDetails = new HashMap<>();
                    if (data.child("name").getValue(String.class).toLowerCase().contains(query.toLowerCase())) {
                        userDetails.put("key", data.getKey());
                        userDetails.put("name", data.child("name").getValue(String.class));
                        userIds.add(userDetails);
                    }
                }
                dataStatus.dataLoaded(userIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Facility> readDataAll(Context context, String str) {
        ArrayList<Facility> dataList = new ArrayList<>();
        InputStream is = context.getResources().openRawResource(R.raw.sports);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens[3].toLowerCase().contains(str.toLowerCase()) || tokens[4].toLowerCase().contains(str.toLowerCase())) {
                    facility = new Facility(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
                    dataList.add(facility);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    public Facility readIndex(Context context, String index) {
        InputStream is = context.getResources().openRawResource(R.raw.sports);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {

            reader.readLine();


            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(",");

                if (tokens[0].equals(index)) {

                    facility = new Facility(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
                    return facility;
                }
            }
        } catch (IOException e) {
            Log.wtf("My activity ", "error");
        }
        return null;
    }


    public interface DataStatus {
        void dataLoaded(Object object);
    }
}

