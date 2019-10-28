package com.example.athletica.data.Search;

import android.content.Context;

import com.example.athletica.data.user.DataManager;
import com.example.athletica.ui.search.SearchResultActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchController {


    private ArrayList<String> facilityName = new ArrayList<>(); // names of all the facilities are stored in this list
    private ArrayList<String> facilityIds = new ArrayList<>();

    private ArrayList<String> eventsName = new ArrayList<String>();// unique indexes of all the records are stored in this list
    private ArrayList<String> eventIds = new ArrayList<>();

    private ArrayList<String> userName = new ArrayList<String>();// unique indexes of all the records are stored in this list
    private ArrayList<String> userIds = new ArrayList<>();

    //private ArrayList<Event> eventsList=new ArrayList<>();      // similar to facciities, details of events have to be stored, this has not been implemented yet.
    private ArrayList<Map> facilityMap;
    private ArrayList<Map> eventMap;
    private ArrayList<Map> userMap;


    //Map<String,ArrayList> Tmap;
    HashMap<String, ArrayList> Tmap;

    public DataManager dataManager;


    private String value;
    Context context;

    public SearchController(Context context, String value) {
        this.Tmap=new HashMap<>();
        this.context=context;
        this.value=value;
        dataManager=new DataManager();
    }



    public Map<String, ArrayList> getFacilities() {
        facilityMap = (ArrayList<Map>) dataManager.readDataAll(context, value);
        HashMap<String, ArrayList> mMap = new HashMap<>();

        for (Map<String, String> map :facilityMap) {
            String str2 = map.get("name");  //
            String index = map.get("index");
            facilityName.add(str2);
            facilityIds.add(index);
        }
        mMap.put("name", facilityName);
        mMap.put("key", facilityIds);
        return mMap;

    }


    public void getEvents(final SearchResultActivity searchResultActivity) {
        dataManager.getEventKeys(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                eventMap = ((ArrayList<Map>) object);
                for (Map<String, String> map : eventMap) {
                    String str1 = map.get("key");
                    String str2 = map.get("name");
                    eventIds.add(str1);
                    eventsName.add(str2);
                }
                Tmap.put("names", eventsName);
                Tmap.put("key", eventIds);
                searchResultActivity.initRecyclerView(Tmap.get("names"),Tmap.get("key"),1);
            }
        }, 20, value);

    }

//    private Map<String, ArrayList> EventParser(ArrayList<Map> sample) {
//        HashMap<String, ArrayList> mMap = new HashMap<>();
//        for (Map<String, String> map : sample) {
//            String str1 = map.get("key");
//            String str2 = map.get("name");
//            eventIds.add(str1);
//            eventsName.add(str2);
//        }
//        mMap.put("names", eventsName);
//        mMap.put("key", eventIds);
//        return mMap;
//    }




}
