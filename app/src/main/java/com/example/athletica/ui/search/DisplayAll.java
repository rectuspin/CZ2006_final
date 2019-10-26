package com.example.athletica.ui.search;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athletica.R;
import com.example.athletica.data.user.DataManager;

import java.util.ArrayList;
import java.util.Map;

public class DisplayAll extends AppCompatActivity {



    private ArrayList<String> facilities=new ArrayList<>();
    private ArrayList<String> index=new ArrayList<>();


    private ArrayList<String> eventsName = new ArrayList<String>();// unique indexes of all the records are stored in this list
    private ArrayList<String> eventIds = new ArrayList<>();

    private ArrayList<String> userNames = new ArrayList<String>();
    private ArrayList<String> userIds = new ArrayList<>();



    private ArrayList<Map> eventMap;
    private ArrayList<Map> facilityMap;
    private ArrayList<Map> userMap;

    private int state;

    private DataManager dataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all);


        state=Integer.parseInt(getIntent().getStringExtra("state"));
        dataManager = new DataManager();

        if(state==0) getFacilities();
        else if(state==1) getEvents();
        else getUsers();
    }

    private void getFacilities(){
        facilityMap= (ArrayList<Map>) dataManager.readDataAll(this,"");
        FacilityParser(facilityMap);
    }

    private void getEvents(){
        dataManager.getEventKeys(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                eventMap = ((ArrayList<Map>)object);
                EventParser(eventMap);
            }
        }, 20,"");


    }
    private void EventParser(ArrayList<Map> sample){
        for(Map<String, String> map:sample){
            String str1 = map.get("key");
            String str2 = map.get("name");
            eventIds.add(str1);
            eventsName.add(str2);
        }
        initRecyclerView(state,eventsName,eventIds);
    }


    private void getUsers(){
        dataManager.getAllUsers(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                userMap = ((ArrayList<Map>)object);
                UserParser(userMap);
            }
        }, "");


    }


    private void UserParser(ArrayList<Map> sample){
        for(Map<String, String> map:sample){
            String str1 = map.get("key");
            String str2 = map.get("name");
            userIds.add(str1);
            userNames.add(str2);
        }
        initRecyclerView(state,userNames,userIds);
    }



    private void FacilityParser(ArrayList<Map> sample){
        for(Map<String, String> map:sample){
            String str2=map.get("name");  //parsing facilities and index's in separate lists
            String i=map.get("index");
            facilities.add(str2);
            index.add(i);
        }
        initRecyclerView(state,facilities,index);

    }
    private void initRecyclerView(int id,ArrayList<String> names,ArrayList<String> index ){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView=findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(layoutManager);
        Layout_mainpage  adapter = new Layout_mainpage(this,names,index,id);
        recyclerView.setAdapter(adapter);
    }

}
