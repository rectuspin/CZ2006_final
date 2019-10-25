package com.example.athletica.data.user;

import androidx.annotation.NonNull;

import com.example.athletica.data.event.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private FirebaseDatabase firebaseDatabase;
    private Object object;

    public DataManager() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public interface DataStatus {
        void dataLoaded(Object object);
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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Map> eventIds = new ArrayList<>();
                long amountToDownload = amount;
                int counter = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Map<String, String> eventDetails = new HashMap<>();

                    if (data.child("name").getValue(String.class).contains(str) || data.child("description").getValue(String.class).contains(str)) {
                        eventDetails.put("key", data.getKey());
                        eventDetails.put("name", data.child("name").getValue(String.class));
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
        databaseReference.addValueEventListener(new ValueEventListener() {
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


    public void getAllUsers(final DataStatus dataStatus, final String str) {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users_info");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Map> userIds = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Map<String, String> userDetails = new HashMap<>();
                    if (data.child("name").getValue(String.class).contains(str)) {
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

}

