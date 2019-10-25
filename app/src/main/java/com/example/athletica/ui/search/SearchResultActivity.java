package com.example.athletica.ui.search;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athletica.R;
import com.example.athletica.data.SearchCsv;
import com.example.athletica.data.user.DataManager;

import java.util.ArrayList;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener {

/*
    private ArrayList<String> facilittIdList, facilityNameList, eventIdList, eventNameList, userIdList, userNameList;
    private ArrayList<Map> facilitiesMap, eventsMap, usersMap;
    private DataManager dataManager;
    private String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        facilittIdList = new ArrayList<>();
        facilityNameList = new ArrayList<>();
        eventIdList = new ArrayList<>();
        eventNameList = new ArrayList<>();
        userIdList = new ArrayList<>();
        userNameList = new ArrayList<>();
        facilitiesMap = (ArrayList<Map>) getIntent().getSerializableExtra("Facility_list");

        dataManager = new DataManager();
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);

            init_1(facilitiesMap);
            getEvents();
            getUsers();
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                SearchCsv searchCsv = new SearchCsv(this);
                facilitiesMap = (ArrayList<Map>) searchCsv.readDataAll("");

                Intent intent = new Intent(SearchResultActivity.this, DisplayAll.class);
                intent.putExtra("facilities", facilitiesMap);
                intent.putExtra("state", "0");
                startActivity(intent);
                break;
        }

    }



    private void getFacilitySearchResult(ArrayList<Map> facilitiesMap) {
        for (Map<String, String> map : facilitiesMap) {
            String facilityID = map.get("index");
            String facilityName = map.get("name");

            facilittIdList.add(facilityID);
            facilityNameList.add(facilityName);
        }
        initFacilityRecyclerView();
    }


    private void getEventSearchResult(ArrayList<Map> eventsMap) {
        for (Map<String, String> map : eventsMap) {
            String eventId = map.get("key");
            String eventName = map.get("name");
            eventIdList.add(eventId);
            eventNameList.add(eventName);
        }
    }


    private void getUserSearchResult(ArrayList<Map> usersMap) {
        for (Map<String, String> map : usersMap) {
            String userId = map.get("key");
            String userName = map.get("name");
            userIdList.add(userId);
            userNameList.add(userName);
        }
    }

    private void initFacilityRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.rvFacility);
        recyclerView.setLayoutManager(layoutManager);
        SearchResultRecyclerViewAdapter adapter = new SearchResultRecyclerViewAdapter(this, facilityNameList, facilittIdList, 0);
        recyclerView.setAdapter(adapter);
    }

    private void initEventRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.rvFacility);
        recyclerView.setLayoutManager(layoutManager);
        SearchResultRecyclerViewAdapter adapter = new SearchResultRecyclerViewAdapter(this, eventNameList, eventIdList, 1);
        recyclerView.setAdapter(adapter);
    }

    private void initUserRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.rvFacility);
        recyclerView.setLayoutManager(layoutManager);
        SearchResultRecyclerViewAdapter adapter = new SearchResultRecyclerViewAdapter(this, userNameList, userIdList, 2);
        recyclerView.setAdapter(adapter);
    }

    private void getUsers() {
        dataManager.getUser(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                usersMap = ((ArrayList<Map>) object);
                getUserSearchResult(usersMap);
            }
        }, query);
        initUserRecyclerView();
    }


    private void getEvents() {
        dataManager.getEventKeys(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                eventsMap = ((ArrayList<Map>) object);
                getEventSearchResult(eventsMap);

            }
        }, 20, query);
        initEventRecyclerView();

    }

 */


    private ArrayList<String> facilities = new ArrayList<>(); // names of all the facilities are stored in this list
    private ArrayList<String> index = new ArrayList<>();

    private ArrayList<String> eventsName = new ArrayList<String>();// unique indexes of all the records are stored in this list
    private ArrayList<String> eventIds = new ArrayList<>();

    private ArrayList<String> userName = new ArrayList<String>();// unique indexes of all the records are stored in this list
    private ArrayList<String> userIds = new ArrayList<>();


    //private ArrayList<Event> eventsList=new ArrayList<>();      // similar to facciities, details of events have to be stored, this has not been implemented yet.
    private ArrayList<Map> sample;
    private ArrayList<Map> eventMap;
    private ArrayList<Map> userMap;

    private String str;
    private DataManager dataManager;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        TextView viewAllText = findViewById(R.id.button1);
        viewAllText.setOnClickListener(this);
        //progressDialog = new ProgressDialog(button1.getContext());
        //initRecyclerView2();
        SearchCsv searchCsv = new SearchCsv(this);
        dataManager = new DataManager();

//        sample= (ArrayList<Map>)getIntent().getSerializableExtra("Facility_list");


        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            str = intent.getStringExtra(SearchManager.QUERY);
        }
        sample = (ArrayList<Map>) searchCsv.readDataAll(str);
        init_1(sample);
        getEvents();
        getUsers();
    }

    //
    private void getEvents() {
        dataManager.getEventKeys(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                eventMap = ((ArrayList<Map>) object);
                System.out.println(eventMap.size());
                init_2(eventMap);
            }
        }, 20, str);
        initRecyclerView2();

    }

    private void getUsers() {
        dataManager.getAllUsers(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                userMap = ((ArrayList<Map>) object);
                init_3(userMap);
            }
        }, str);
        initRecyclerView3();
    }


    // this is used to initialize the first recycler view (to display the facilities)

    private void init_1(ArrayList<Map> sample) {
        for (Map<String, String> map : sample) {
            String str2 = map.get("name");  //parsing facilities and index's in separate lis
            String i = map.get("index");
            facilities.add(str2);
            index.add(i);
        }

        initRecyclerView1();
    }

    // this is used to initialize the first recycler view (to display the Events)

    private void init_2(ArrayList<Map> sample) {


        for (Map<String, String> map : sample) {
            String str1 = map.get("key");
            String str2 = map.get("name");
            eventIds.add(str1);
            eventsName.add(str2);
        }

        initRecyclerView2();
    }


    private void init_3(ArrayList<Map> sample) {
        for (Map<String, String> map : sample) {
            String str1 = map.get("key");
            String str2 = map.get("name");
            userIds.add(str1);
            userName.add(str2);
        }

        initRecyclerView3();
    }


    private void initRecyclerView1() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.rvFacility);
        recyclerView.setLayoutManager(layoutManager);
        SearchResultRecyclerViewAdapter adapter = new SearchResultRecyclerViewAdapter(this, facilities, index, 0);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerView2() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.rvEvent);
        recyclerView.setLayoutManager(layoutManager);
        SearchResultRecyclerViewAdapter adapter = new SearchResultRecyclerViewAdapter(this, eventsName, eventIds, 1);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerView3() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setLayoutManager(layoutManager);
        SearchResultRecyclerViewAdapter adapter = new SearchResultRecyclerViewAdapter(this, userName, userIds, 2);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                SearchCsv searchCsv = new SearchCsv(this);

                sample = (ArrayList<Map>) searchCsv.readDataAll("");

                Intent intent = new Intent(SearchResultActivity.this, DisplayAll.class);
                intent.putExtra("Array_List", sample);
                intent.putExtra("state", "0");
                startActivity(intent);
                break;
        }
    }

}