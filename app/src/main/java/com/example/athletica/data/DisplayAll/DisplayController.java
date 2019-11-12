package com.example.athletica.data.DisplayAll;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.facility.Facility;
import com.example.athletica.data.profile.ProfileManager;
import com.example.athletica.data.search.Filter;
import com.example.athletica.data.user.DataManager;
import com.example.athletica.data.user.User;
import com.example.athletica.data.user.UserProfile;
import com.example.athletica.ui.home.HomeActivity;
import com.example.athletica.ui.profile.ViewProfileActivity;
import com.example.athletica.ui.search.DisplayAll;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;


public class DisplayController {
    Context context;
    DataManager dataManager;
    Filter filter;
    public static User user;
    ProfileManager profileManager;


    private ArrayList<String> facilities = new ArrayList<>();
    private ArrayList<String> index = new ArrayList<>();


    private ArrayList<String> eventsName = new ArrayList<String>();// unique indexes of all the records are stored in this list
    private ArrayList<String> eventIds = new ArrayList<>();

    private ArrayList<String> userNames = new ArrayList<String>();
    private ArrayList<String> userIds = new ArrayList<>();


    private ArrayList<Map> eventMap;
    private ArrayList<Facility> facilityMap;
    private ArrayList<Map> userMap;

    private int state;


    public DisplayController(Context context, int state) {
        this.context = context;
        this.state = state;
        dataManager = new DataManager();
        profileManager = new ProfileManager(context);
        filter = new Filter();
    }

    public void getFacilities(final DisplayAll displayAll) {
        facilityMap = dataManager.readDataAll(context, "");
        for (Facility facility : facilityMap) {
            String str2 = facility.getName();  //parsing facilities and index's in separate lists
            String i = facility.getFacilityIndex();
            facilities.add(str2);
            index.add(i);
        }
        displayAll.initRecyclerView(state, facilities, index);
    }


    public void getEvents(final DisplayAll displayAll) {
        dataManager.getEventKeys(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                eventMap = ((ArrayList<Map>) object);
                filter.endEventCheck(eventMap);
                for (Map<String, String> map : eventMap) {
                    String str1 = map.get("key");
                    String str2 = map.get("name");
                    eventIds.add(str1);
                    eventsName.add(str2);
                }
                displayAll.initRecyclerView(state, eventsName, eventIds);

            }
        }, "");
    }


    public void getEvents(final HomeActivity homeActivity) {
        dataManager.getEventKeys(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                eventMap = ((ArrayList<Map>) object);
                filter.endEventCheck(eventMap);
                filter.sortEvents(eventMap);
                filter.truncateEvents(eventMap, 5);
                for (Map<String, String> map : eventMap) {

                    String str1 = map.get("key");
                    String str2 = map.get("name");
                    eventIds.add(str1);
                    eventsName.add(str2);
                }

                homeActivity.initRecyclerView(1, eventsName, eventIds);
            }
        }, "");
    }


    public void getUsers(final DisplayAll displayAll) {
        dataManager.getAllUsers(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                userMap = ((ArrayList<Map>) object);
                for (Map<String, String> map : userMap) {
                    String str1 = map.get("key");
                    String str2 = map.get("name");
                    userIds.add(str1);
                    userNames.add(str2);
                }
                displayAll.initRecyclerView(state, userNames, userIds);

            }
        }, "");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Facility> sortFacilityByName() {
        ArrayList<Facility> facilities = dataManager.readDataAll(context, "");
        facilities.sort(Comparator.comparing(Facility::getName));
        return facilities;
    }


    public void getSortedEvents(final DisplayAll displayAll) {
        dataManager.getEventKeys(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                eventMap = ((ArrayList<Map>) object);
                filter.endEventCheck(eventMap);


                for (Map<String, String> map : eventMap) {

                    String str1 = map.get("key");
                    String str2 = map.get("name");
                    eventIds.add(str1);
                    eventsName.add(str2);
                }


            }
        }, "");

        for (int i = eventsName.size() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (eventsName.get(j).compareToIgnoreCase(eventsName.get(j + 1)) > -1) {
                    String tempName = eventsName.get(j);
                    eventsName.set(j, eventsName.get(j + 1));
                    eventsName.set(j + 1, tempName);

                    String tempId = eventIds.get(j);
                    eventIds.set(j, eventIds.get(j + 1));
                    eventIds.set(j + 1, tempId);

                }
            }
        }
        displayAll.initRecyclerView(1, eventsName, eventIds);
    }

    public void getSortedUsers(final DisplayAll displayAll) {
        dataManager.getAllUsers(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                userMap = ((ArrayList<Map>) object);
                for (Map<String, String> map : userMap) {
                    String str1 = map.get("key");
                    String str2 = map.get("name");
                    userIds.add(str1);
                    userNames.add(str2);
                }


            }
        }, "");
        for (int i = userNames.size() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (userNames.get(j).compareToIgnoreCase(userNames.get(j + 1)) > -1) {
                    String tempName = userNames.get(j);
                    userNames.set(j, userNames.get(j + 1));
                    userNames.set(j + 1, tempName);

                    String tempId = userIds.get(j);
                    userIds.set(j, userIds.get(j + 1));
                    userIds.set(j + 1, tempId);
                }
            }
        }
        displayAll.initRecyclerView(2, userNames, userIds);
    }
}
