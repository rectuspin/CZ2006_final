package com.example.athletica.data.DisplayAll;

import android.content.Context;

import com.example.athletica.data.facility.Facility;
import com.example.athletica.data.user.DataManager;
import com.example.athletica.ui.home.HomeActivity;
import com.example.athletica.ui.search.DisplayAll;

import java.util.ArrayList;
import java.util.Map;

public class DisplayController {
    private String value;
    Context context;
    DataManager dataManager;



    private ArrayList<String> facilities=new ArrayList<>();
    private ArrayList<String> index=new ArrayList<>();


    private ArrayList<String> eventsName = new ArrayList<String>();// unique indexes of all the records are stored in this list
    private ArrayList<String> eventIds = new ArrayList<>();

    private ArrayList<String> userNames = new ArrayList<String>();
    private ArrayList<String> userIds = new ArrayList<>();



    private ArrayList<Map> eventMap;
    private ArrayList<Facility> facilityMap;
    private ArrayList<Map> userMap;

    private int state;










    public DisplayController(Context context,int state){
        this.context=context;
        this.state=state;
        dataManager=new DataManager();
    }

    public void getFacilities(final DisplayAll displayAll){
        facilityMap= (ArrayList<Facility>)dataManager.readDataAll(context,"");
        for(Facility facility:facilityMap){
            String str2=facility.getName();  //parsing facilities and index's in separate lists
            String i=facility.getFacilityIndex();
            facilities.add(str2);
            index.add(i);
        }
        displayAll.initRecyclerView(state,facilities,index);
    }


    public void getEvents(final DisplayAll displayAll){
        dataManager.getEventKeys(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                eventMap = ((ArrayList<Map>)object);
                for(Map<String, String> map:eventMap){
                    String str1 = map.get("key");
                    String str2 = map.get("name");
                    eventIds.add(str1);
                    eventsName.add(str2);
                }
                displayAll.initRecyclerView(state,eventsName,eventIds);

            }
        }, 100,"");
    }

    public void getFeaturedEvents(final HomeActivity homeActivity){
        dataManager.getEventKeys(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                eventMap = ((ArrayList<Map>)object);
                for(Map<String, String> map:eventMap){
                    String str1 = map.get("key");
                    String str2 = map.get("name");
                    eventIds.add(str1);
                    eventsName.add(str2);
                }
                homeActivity.initRecyclerView(1,eventsName,eventIds);
            }
        }, 4,"");
    }


    public void getUsers(final DisplayAll displayAll){
        dataManager.getAllUsers(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                userMap = ((ArrayList<Map>)object);
                for(Map<String, String> map:userMap){
                    String str1 = map.get("key");
                    String str2 = map.get("name");
                    userIds.add(str1);
                    userNames.add(str2);
                }
                displayAll.initRecyclerView(state,userNames,userIds);

            }
        }, "");
    }
}
